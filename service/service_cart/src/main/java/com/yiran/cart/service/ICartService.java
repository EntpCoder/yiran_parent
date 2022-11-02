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
     * 根据用户id查询用户购物车数据,也可以传入购物车id数组,只把指定购物车id信息返回
     * (用于用户购物车查询，和勾选购物车信息后确认订单页面查根据购物车id询商品信息)
     * @param userId 用户id
     * @param cartIds cartIds
     * @return 用户的购物车
     */
    List<CartVO> getUserCart(String userId,String[] cartIds);

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
