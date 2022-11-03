package com.yiran.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.cart.mapper.*;
import com.yiran.cart.service.ICartService;

import com.yiran.model.entity.*;
import com.yiran.model.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<CartVO> getUserCart(String userId,String[] cartIds) {
        List<CartVO> cartVos = new ArrayList<>();
        // 购物车查询
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(userId)){
            cartQueryWrapper.eq("user_id",userId);
        }
        List<Cart> carts = cartMapper.selectList(cartQueryWrapper);
        // 如果根据指定购物车id批量查询购物车数据(批量选择商品下订单时)
        if(cartIds != null && cartIds.length != 0){
            Set<String> cartIdSet = new HashSet<>(Arrays.asList(cartIds));
            carts = carts.stream()
                    .filter(c -> cartIdSet.contains(c.getCartId()))
                    .collect(Collectors.toList());
        }
        System.out.println(carts);
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

    /**
     * 购物车增加
     * @param cart 购物车对象
     * @return 返回对象
     */
    @Override
    public boolean saveCart(Cart cart){
        System.out.println(cart);
        return cartMapper.insert(cart) > 0;
    }

    /**
     * 购物车单条记录删除
     * @param cartId 购物车id
     * @return 对象
     */
    @Override
    public boolean deleteCartById(String cartId){
        return cartMapper.deleteById(cartId) > 0;
    }

    /**
     * 购物车批量结算-批量删除
     * @param cartId 购物车id
     * @return 对象
     */
    @Override
    public boolean deleteCartByIds(String[] cartId){
        return cartMapper.deleteBatchIds(Arrays.asList(cartId)) > 0;
    }

    @Override
    public boolean increaseQuantity(String cartId,Integer nums) {
        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setNums(nums);
        return cartMapper.updateById(cart) > 0;
    }
}
