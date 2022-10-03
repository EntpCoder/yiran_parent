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
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞id
     */
    @TableId
    private String likeId;

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 点赞数量
     */
    private Integer num;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 点赞时间
     */
    private LocalDateTime time;

    /**
     * 点赞状态  1点赞  0未点赞
     */
    private Boolean status;
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
