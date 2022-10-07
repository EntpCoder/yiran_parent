package com.yiran.cart.service;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface ICartService {
    //查询
    ResultCodeEnum getById(String cartId);
    //增
    ResultCodeEnum save(Cart cart);
    //删
    ResultCodeEnum delete(String cartId);
    //改
    ResultCodeEnum update(Cart cart);
}
