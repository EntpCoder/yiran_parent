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
@TableName("`user`")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * openid
     */
    private String openId;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别
     */
    private Boolean gender;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像地址
     */
    private String image;

    /**
     * 用户生日
     */
    private LocalDateTime birthday;
    /**
     * 用户默认收货地址id
     */
    private String defaultAddressId;
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
