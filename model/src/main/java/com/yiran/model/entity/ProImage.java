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
@TableName("pro_image")
public class ProImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品图片id
     */
    @TableId
    private String proImageId;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 商品id
     */
    private String proId;
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
