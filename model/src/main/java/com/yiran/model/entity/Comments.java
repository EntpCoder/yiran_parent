package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId
    private String commentId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String proId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 评价内容
     */
    private String text;

    /**
     * 评价图片
     */
    private String img;

    /**
     * 描述相符评分
     */
    private BigDecimal describeScore;

    /**
     * 服务态度评分
     */
    private BigDecimal serviceScore;

    /**
     * 物流服务评分
     */
    private BigDecimal logisticsScore;

    /**
     * 评论时间
     */
    private LocalDateTime time;
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDelete;

    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private LocalDateTime updateTime;

    private String other1;

    private String other2;
}
