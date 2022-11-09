package com.yiran.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.mapper.CouponMapper;
import com.yiran.mapper.OrdersMapper;
import com.yiran.mapper.ReceiveCouponMapper;
import com.yiran.model.entity.Coupon;
import com.yiran.model.entity.Orders;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.model.vo.ReceiveCouponVO;
import com.yiran.service.CouponService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author by LvJunLong
 * @date 2022/11/3
 */
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponMapper couponMapper;
    private final ReceiveCouponMapper receiveCouponMapper;
    private final OrdersMapper ordersMapper;
    private final RedisTemplate<String,Object> redisTemplate;
    private DefaultRedisScript script;
    private final RabbitTemplate rabbitTemplate;

    public CouponServiceImpl(CouponMapper couponMapper, ReceiveCouponMapper receiveCouponMapper, OrdersMapper ordersMapper, RedisTemplate<String, Object> redisTemplate, RabbitTemplate rabbitTemplate) {
        this.couponMapper = couponMapper;
        this.receiveCouponMapper = receiveCouponMapper;
        this.ordersMapper = ordersMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("skillSecond.lua")));
    }

    @Override
    public Boolean createCoupon(Coupon coupon) {
        return couponMapper.insert(coupon) >0;
    }

    @Override
    public List<ReceiveCouponVO> getFailureCoupon(String usrId) {
        List<ReceiveCouponVO> receiveCouponVoS = new ArrayList<>();
        QueryWrapper<ReceiveCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",usrId).eq("status",(byte) 2);
        List<ReceiveCoupon> receiveCoupons = receiveCouponMapper.selectList(queryWrapper);
        for (ReceiveCoupon r:
                receiveCoupons) {
            ReceiveCouponVO receiveCouponVO = new ReceiveCouponVO();
            BeanUtils.copyProperties(r,receiveCouponVO);
            Coupon coupon = couponMapper.selectById(r.getCouponId());
            BeanUtils.copyProperties(coupon,receiveCouponVO);
            receiveCouponVoS.add(receiveCouponVO);
        }
        return receiveCouponVoS;
    }

    @Override
    public BigDecimal consumeCoupon(String receiveId) {
        ReceiveCoupon reCoupon = receiveCouponMapper.selectById(receiveId);
        if (reCoupon.getStatus()==(byte) 0){
            reCoupon.setStatus((byte) 1);
            receiveCouponMapper.updateById(reCoupon);
            Coupon coupon = couponMapper.selectById(reCoupon.getCouponId());
            coupon.setUsedNums(coupon.getUsedNums()+1);
            couponMapper.updateById(coupon);
            return coupon.getDiscountAmount();
        } else{
            return new BigDecimal(0);
        }
    }

    @Override
    public Boolean updateCouponStatus(String orderId) {
        Orders order = ordersMapper.selectById(orderId);
        ReceiveCoupon reCoupon = receiveCouponMapper.selectById(order.getReceiveCouponId());
        if (reCoupon.getStatus()==(byte) 0){
            reCoupon.setStatus((byte) 1);
            return receiveCouponMapper.updateById(reCoupon) > 0;
        } else{
            return false;
        }
    }

    @Override
    public List<Coupon> getCouponList() {
        //加载所有有效的优惠券
        LocalDateTime currentTime = LocalDateTime.now();
        List<Coupon> couponList = couponMapper.selectList(new QueryWrapper<Coupon>().le("grant_start_time",currentTime).ge("grand_end_time",currentTime));
        for (Coupon c:
                couponList) {
            String key = "couponId"+c.getCouponId();
            redisTemplate.opsForValue().set(key,c,24, TimeUnit.HOURS);
        }
        return couponList;
    }

    @Override
    public ReceiveCoupon getReceiveCoupon(String couponId,String userId) {
        ReceiveCoupon receiveCoupon = new ReceiveCoupon();
        //判断用户是否已有此优惠券
        List<String> couponIdList = receiveCouponMapper.selectList(new QueryWrapper<ReceiveCoupon>().select("coupon_id").eq("user_id",userId))
                .stream()
                .map(ReceiveCoupon::getCouponId)
                .collect(Collectors.toList());
        if (couponIdList.contains(couponId)){
            return receiveCoupon;
        }
        // 点击领券时，received_nums+1
        Coupon coupon;
        String key = "coupon_Id"+couponId;
        String uuid = UUID.randomUUID().toString().replace("-","");
        boolean isLock = redisTemplate.opsForValue().setIfAbsent(key,uuid,100,TimeUnit.SECONDS);
        if (isLock){
            coupon = (Coupon) redisTemplate.opsForValue().get("couponId"+couponId);
            if (coupon == null){
                return receiveCoupon;
            }
            if (coupon.getQuota().equals(coupon.getReceivedNums())){
                return receiveCoupon;
            }else {
                //优惠券已领取数量+1
                coupon.setReceivedNums(coupon.getReceivedNums()+1);
                //新增用户领取的优惠券数据
                receiveCoupon.setCouponId(couponId);
                receiveCoupon.setUserId(userId);
                receiveCoupon.setDrawTime(LocalDateTime.now());
                receiveCoupon.setStatus((byte) 0);
                receiveCoupon.setIsDelete(false);
                if (coupon.getTimeType() == (byte) 0){
                    //用户优惠券使用时间
                    receiveCoupon.setStartTime(coupon.getUsageStartTime());
                    receiveCoupon.setExpirationTime(coupon.getGrandEndTime());
                    receiveCouponMapper.insert(receiveCoupon);
                }else {
                    //用户优惠券周期时限
                    receiveCoupon.setStartTime(LocalDateTime.now());
                    receiveCoupon.setExpirationTime(LocalDateTime.now().plusSeconds(coupon.getTimelimit()));
                    receiveCouponMapper.insert(receiveCoupon);
                    //发送延时队列
                    rabbitTemplate.convertAndSend("yiran.delayed.exchange","coupon.failure",receiveCoupon.getReceiveId(),message -> {
                        message.getMessageProperties().setDelay(coupon.getTimelimit()*1000);
                        return message;
                    });
                }
                redisTemplate.opsForValue().set("couponId"+couponId,coupon,24,TimeUnit.HOURS);
                couponMapper.updateById(coupon);
                //lua脚本
                Long execute = (Long) redisTemplate.execute(script, Arrays.asList(key),uuid);
            }
        }else {
            try {
                Thread.sleep(100);
                coupon = (Coupon) redisTemplate.opsForValue().get("couponId"+couponId);
                if(coupon != null && coupon.getQuota()>coupon.getReceivedNums()) {
                    getReceiveCoupon(couponId,userId);
                    return receiveCoupon;
                }else {
                    return receiveCoupon;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return receiveCoupon;
    }

    @Override
    public BigDecimal getDiscountAmount(String receiveId) {
        ReceiveCoupon reCoupon = receiveCouponMapper.selectById(receiveId);
        if (reCoupon.getStatus()==(byte) 0){
            Coupon coupon = couponMapper.selectById(reCoupon.getCouponId());
            return coupon.getDiscountAmount();
        } else{
            return new BigDecimal(0);
        }
    }

    @Override
    public List<ReceiveCouponVO> getUsableCoupon(String usrId) {
        List<ReceiveCouponVO> receiveCouponVoS = new ArrayList<>();
        QueryWrapper<ReceiveCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",usrId).eq("status",(byte) 0);
        List<ReceiveCoupon> receiveCoupons = receiveCouponMapper.selectList(queryWrapper);
        for (ReceiveCoupon r:
             receiveCoupons) {
            ReceiveCouponVO receiveCouponVO = new ReceiveCouponVO();
            BeanUtils.copyProperties(r,receiveCouponVO);
            Coupon coupon = couponMapper.selectById(r.getCouponId());
            BeanUtils.copyProperties(coupon,receiveCouponVO);
            receiveCouponVoS.add(receiveCouponVO);
        }
        return receiveCouponVoS;
    }

    @Override
    public List<ReceiveCouponVO> getUsedCoupon(String usrId) {
        List<ReceiveCouponVO> receiveCouponVoS = new ArrayList<>();
        QueryWrapper<ReceiveCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",usrId).eq("status",(byte) 1);
        List<ReceiveCoupon> receiveCoupons = receiveCouponMapper.selectList(queryWrapper);
        for (ReceiveCoupon r:
                receiveCoupons) {
            ReceiveCouponVO receiveCouponVO = new ReceiveCouponVO();
            BeanUtils.copyProperties(r,receiveCouponVO);
            Coupon coupon = couponMapper.selectById(r.getCouponId());
            BeanUtils.copyProperties(coupon,receiveCouponVO);
            Orders order = ordersMapper.selectOne(new QueryWrapper<Orders>().select("numbers").eq("receive_coupon_id",r.getReceiveId()));
            receiveCouponVO.setNumbers(order.getNumbers());
            receiveCouponVO.setUpdateTime(r.getUpdateTime());
            receiveCouponVoS.add(receiveCouponVO);
        }
        return receiveCouponVoS;
    }

    @RabbitListener(queues = "#{couponFailureQueue.name}")
    public void failureCoupon(String receiveId){
        System.out.println("失效优惠券id"+receiveId);
        ReceiveCoupon receiveCoupon = receiveCouponMapper.selectById(receiveId);
        if (receiveCoupon.getStatus() == (byte) 0){
            receiveCoupon.setStatus((byte) 2);
            receiveCouponMapper.updateById(receiveCoupon);
        }
    }


}
