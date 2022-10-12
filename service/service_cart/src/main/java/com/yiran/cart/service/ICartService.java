package com.yiran.cart.service;


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
}
