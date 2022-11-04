package com.yiran.user.service;

import com.yiran.model.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

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
    List<Comments> selectbyproId(String proId);

    /**
     * 根据用户id查找评论
     * @param userId 用户id
     * @return 商品列表
     */
    List<Comments> selectbyUserId(String userId);

    /**
     * 用户添加评论
     * @param userId 用户id
     * @param proId 商品id
     * @param centent 评论内容
     * @param imgAddr 图片
     * @param describe 描述分
     * @param service 服务分
     * @param logisties 售后服务分
     * @return 判断
     */
    Boolean addComment(String userId,String proId,String content,String imgAddr,Float describe,Float service,Float logisties);
}