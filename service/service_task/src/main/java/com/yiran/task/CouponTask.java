package com.yiran.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.ReceiveCoupon;
import com.yiran.task.mapper.ReceiveCouponMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author by LvJunLong
 * @date 2022/11/7
 */
@Component
public class CouponTask {
    private final ReceiveCouponMapper receiveCouponMapper;

    public CouponTask(ReceiveCouponMapper receiveCouponMapper) {
        this.receiveCouponMapper = receiveCouponMapper;
    }

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
}
