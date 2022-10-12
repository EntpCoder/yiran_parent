package com.yiran.cart.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.cart.mapper.*;
import com.yiran.cart.service.ICartService;

import com.yiran.common.result.R;
import com.yiran.model.entity.*;
import com.yiran.model.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private final CartMapper cartMapper;
    private final ColorMapper colorMapper;
    private final ProAttributeInfoMapper proAttributeInfoMapper;
    private final SizeMapper sizeMapper;
    private final ProductMapper productMapper;

    public CartServiceImpl(CartMapper cartMapper, ColorMapper colorMapper,
                           ProAttributeInfoMapper proAttributeInfoMapper,
                           SizeMapper sizeMapper, ProductMapper productMapper) {
        this.cartMapper = cartMapper;
        this.colorMapper = colorMapper;
        this.proAttributeInfoMapper = proAttributeInfoMapper;
        this.sizeMapper = sizeMapper;
        this.productMapper = productMapper;
    }

    @Override
    public List<CartVO> getUserCart(String userId) {
        List<CartVO> cartVos = new ArrayList<>();
        // 购物车查询
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        List<Cart> carts = cartMapper.selectList(cartQueryWrapper);
        for (Cart c:
             carts) {
            // 一条购物车的数据
            CartVO cartVO = new CartVO();
            BeanUtils.copyProperties(c,cartVO);
            // 获取对应的商品信息
            ProAttributeInfo proAttributeInfo = proAttributeInfoMapper.selectById(c.getProAttributeInfoId());
            // 根据商品信息表中的商品id去查商品
            Product product = productMapper.selectById(proAttributeInfo.getProId());
            BeanUtils.copyProperties(product,cartVO);
            // 根据商品信息表中的商品id去查颜色
            Color color = colorMapper.selectById(proAttributeInfo.getColorId());
            BeanUtils.copyProperties(color,cartVO);
            // 根据商品信息表中的商品id去查尺寸
            Size size = sizeMapper.selectById(proAttributeInfo.getSizeId());
            BeanUtils.copyProperties(size,cartVO);
            cartVos.add(cartVO);
        }
        return cartVos;
    }
}