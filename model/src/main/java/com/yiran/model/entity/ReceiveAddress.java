package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@TableName("receive_address")
public class ReceiveAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收获地址id
     */
    @TableId
    private String receiveId;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 收获手机
     */
    private String phone;

    /**
     * 可收货时间,0：周一至周日均可收获 1：周一至周五可收货 2：周六日、节假日可收货
     */
    private Byte time;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String area;

    /**
     * 街道
     */
    private String stree;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 地址类型 0：家庭 1：公司 2：其他
     */
    private Byte type;

    /**
     * 用户id
     */
    private String userId;
    @TableField(exist = false)
    private boolean isDefault;
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
