package com.yiran.user.service;

import com.yiran.model.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiran.model.vo.CommentVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface ICommentsService extends IService<Comments> {
    /**
     * 根据商品id查找评论
     * @param proId 商品Id
     * @return 商品列表
     */
    List<CommentVo> selectbyproId(String proId);


    /**
     * 用户添加评论
     * @param userId 用户id
     * @param proId 商品id
     * @param content 评论内容
     * @param imgAddr 图片
     * @param describe 描述分
     * @param service 服务分
     * @param logisties 售后服务分
     * @return 判断
     */
    Boolean addComment(String userId,String proId,String content,String imgAddr,Float describe,Float service,Float logisties);
}
