package com.yiran.cart.service.Impl;

import com.yiran.cart.mapper.CartMapper;
import com.yiran.cart.service.ICartService;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class CartServiceImpl  implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    //查找购物车数据——查询
    @Override
    public ResultCodeEnum getById(String cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart ==null){
            //失败
            return ResultCodeEnum.FAIL;
        }else{
            //成功
            return ResultCodeEnum.SUCCESS;
        }
    }
    //添加购物车数据——增
    @Override
    public ResultCodeEnum save(Cart cart) {
        int mapp = cartMapper.insert(cart);
        if (mapp > 0){
            //添加成功
            return ResultCodeEnum.SUCCESS;
        }
            //添加失败
            return ResultCodeEnum.FAIL;
    }
    //清空购物车数据——删
    @Override
    public ResultCodeEnum delete(String cartId) {
        int mapp = cartMapper.deleteById(cartId);
        if (mapp > 0 ){
            return ResultCodeEnum.FAIL;
        }
        return ResultCodeEnum.SUCCESS;
    }
    //更改购物车数据——改
    @Override
    public ResultCodeEnum update(Cart cart) {
        int mapp = cartMapper.updateById(cart);
        if (mapp > 0 ){
            return ResultCodeEnum.FAIL;
        }
        return ResultCodeEnum.SUCCESS;
    }
}
