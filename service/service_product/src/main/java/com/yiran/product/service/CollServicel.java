package com.yiran.product.service;

import com.yiran.model.vo.CollectionsVO;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/10/15 16:12
 */
public interface CollServicel {
    /**
     *根据用户的id查看个人收藏
     * @param userId 用户id
     * @return List
     */
    List<CollectionsVO> getUserCollections(String userId);

    /**
     * 添加收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否收藏成功 boolean
     */
    Boolean increaseCollections(String proId,String userId);
    /**
     * 取消收藏
     * @param proId 商品id
     * @param userId 用户id
     * @return 是否取消收藏成功 boolean
     */
    Boolean deleCollerticon(String proId,String userId);

    /**
     * 查询用户是否收藏商品
     * @param proId 商品id
     * @param userId 用户id
     * @return boolean
     */
    Boolean chaxun(String proId,String userId);
}
