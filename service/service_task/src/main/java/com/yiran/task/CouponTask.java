package com.yiran.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.Coupon;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.task.mapper.CouponMapper;
import com.yiran.task.mapper.ReceiveCouponMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author by LvJunLong
 * @date 2022/11/7
 */
@Component
public class CouponTask {
    private final ReceiveCouponMapper receiveCouponMapper;
    private final RedisTemplate<String,Object> redisTemplate;
    private final CouponMapper couponMapper;
    public CouponTask(ReceiveCouponMapper receiveCouponMapper, RedisTemplate<String, Object> redisTemplate, CouponMapper couponMapper) {
        this.receiveCouponMapper = receiveCouponMapper;
        this.redisTemplate = redisTemplate;
        this.couponMapper = couponMapper;
    }

    //每天0点定时失效优惠券

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCoupon(){
        LocalDateTime currentTime = LocalDateTime.now();
        List<ReceiveCoupon> receiveCoupons = receiveCouponMapper.selectList(new QueryWrapper<ReceiveCoupon>().eq("status",(byte) 0).le("expiration_time",currentTime));
        for (ReceiveCoupon r:
             receiveCoupons) {
            r.setStatus((byte) 2);
            receiveCouponMapper.updateById(r);
        }
    }

    //每天0点加载秒杀优惠券到redis

    @Scheduled(cron = "0 0 0 * * ?")
    public void addCouponListRedis(){
        //加载所有有效的优惠券
        LocalDateTime currentTime = LocalDateTime.now();
        List<Coupon> couponList = couponMapper.selectList(new QueryWrapper<Coupon>().le("grant_start_time",currentTime).ge("grand_end_time",currentTime));
        for (Coupon c:
                couponList) {
            String key = "couponId"+c.getCouponId();
            redisTemplate.opsForValue().set(key,c,24, TimeUnit.HOURS);
        }
    }
}
