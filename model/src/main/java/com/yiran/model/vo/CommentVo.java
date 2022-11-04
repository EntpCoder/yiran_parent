package com.yiran.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author weiyuwen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    /**
     * 用户头像
     */
    private String image;
    private String userName;
    private LocalDateTime createTime;
    private String text;
    /**
     * 评论中的图片
     */
    private String img;
    private BigDecimal satisfactionScore;
}