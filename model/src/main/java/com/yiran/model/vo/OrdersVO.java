package com.yiran.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/7 19:53
 */
@Data
public class OrdersVO implements Serializable {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String numbers;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 下单时间
     */
    private LocalDateTime placeTime;
    /**
     * 商品数量
     */
    private Integer proNum;
    /**
     * 订单状态：1未支付2已支付3已取消4待发货5待收货6已完成
     */
    private Byte orderState;
    /**
     * 外键用户id
     */
    private String usersId;
    /**
     * 收货地址id
     */
    private String receiveId;
    /**
     * 订单详情信息
     */
    private List<OrderDetailsVo> orderDetails;

}
