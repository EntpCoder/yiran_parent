package com.yiran.product.service;

import com.yiran.model.entity.Collections;
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
     */
    List<CollectionsVO> getUserCollections(String userId);

    /**
     * 根据商品的id来新增
     * @param collections 购物车
     */
    Boolean increaseCollections(Collections collections);
    /**
     * 根据收场的id来取消收藏
     * @param collectionId 收藏id
     */
    Boolean deleCollerticon(String collectionId);
}
