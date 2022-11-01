package com.yiran.cart.controller;

import com.yiran.cart.service.ICartService;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Cart;
import com.yiran.model.vo.CartVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 小番茄
 * @date 2022/10/88:38
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    private final ICartService cartService;
    public CartController(ICartService cartService){
        this.cartService = cartService;
    }
    @GetMapping("/userCart/{userId}")
    public R<List<CartVO>> userCart(@PathVariable("userId") String userId){
        List<CartVO> cartList = cartService.getUserCart(userId);
        return cartList == null ? R.fail() : R.ok("cartList",cartList);
    }

    /**
     * 购物车添加
     * @param cart 购物车对象
     * @return 返回R
     */
    @PostMapping("/save")
    public R<Boolean> saveCart(Cart cart){
        return cartService.saveCart(cart) ? R.ok("isDelete",true):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 根据购物车id来删除单条购物车里的数据
     * @param cartId 购物车对象
     * @return R
     */
    @DeleteMapping("/deleCart/{cartId}")
    public R<Boolean> deleCart(@PathVariable("cartId") String cartId){
        return cartService.deleteCartById(cartId)?R.ok("isDelete",true):R.fail(ResultCodeEnum.DATA_EMPTY);
    }

    /**
     * 根据购物车id来结算订单--批量删除
     * @param cartIds 购物车id
     * @return R
     */
    @DeleteMapping("/deleaddCart")
    public R<Boolean> deleaddCart(String[] cartIds){
        return cartService.deleteCartByIds(cartIds) ? R.ok("isDelete",true):R.fail(ResultCodeEnum.FAIL);
    }
    @PostMapping("/updataNums")
    public R<Boolean> updateNumByCartId(String cartId, Integer nums){
        return cartService.increaseQuantity(cartId, nums) ? R.ok("isDelete",true):R.fail(ResultCodeEnum.DATA_EMPTY);
    }
}

