package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;
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
