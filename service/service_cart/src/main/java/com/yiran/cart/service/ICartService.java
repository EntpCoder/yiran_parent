package com.yiran.cart.service;


import com.yiran.model.entity.Cart;
import com.yiran.model.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface ICartService {
    /**
     * 根据用户id查询用户购物车数据
     * @param userId 用户id
     * @return 用户的购物车
     */
    List<CartVO> getUserCart(String userId);

    /**
     *根据商品属性id来是实现购物车商品的-增加
     * @param cart 购物车对象
     * @return 用户的购物车
     */
    boolean saveCart(Cart cart);

    /**
     * 根据购物车单体结算来-单条删除
     * @param cartId 购物车id
     * @return 购物车
     */
    boolean deleteCartById(String cartId);

    /**
     * 购物车批量结算-批量删除
     * @param cartId 购物车id
     * @return 购物车
     */
    boolean deleteCartByIds(String[] cartId);

    /**
     * 增加购物数量
     * @param cartId 购物车id
     * @param nums 数量
     * @return 是否成功
     */
    boolean increaseQuantity(String cartId,Integer nums);

}
