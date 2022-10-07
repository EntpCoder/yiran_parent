package com.yiran.cart.controller;

import com.yiran.cart.service.ICartService;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Yang Song
 * @date 2022/10/1 18:07
 */
@RestController
@RequestMapping("/cart")
public class CartTestController {
    @Autowired
    private ICartService service;
    @GetMapping("/test")
    public R<String> testCart(){
        return R.ok("testData","测试数据 --宋洋");
    }

    //1、查
    @GetMapping("add/{Id}")
    public ModelAndView getBsyId(@PathVariable("Id") String cartId){
        ResultCodeEnum resultCodeEnum = service.getById(cartId);
        //数据绑定
        ModelAndView mav = new ModelAndView();
        //
        mav.addObject("resultCodeEnum" + resultCodeEnum);
        mav.setViewName("");
        return mav;
    }
    //2、添加购物车数据——增
    @PostMapping("Carts")
    public ModelAndView save(Cart cart){
        cart.setCartId("1102");
        cart.setUserId("101");
        cart.setProAttributeInfoId("22");
        cart.setNums(12);
       ResultCodeEnum res = service.save(cart);
       ModelAndView mav = new ModelAndView();
       mav.addObject("res" + res);
       mav.setViewName("");
       return mav;
    }
    //3、改
    @GetMapping("update")
    public ModelAndView updateById(@RequestParam String cartId){
        ResultCodeEnum res = service.getById(cartId);
        ModelAndView mav = new ModelAndView();
        //有点小问题
        mav.addObject("res",res.getCode());
        mav.setViewName("");
        return mav;
    }
    @PutMapping("add/updete")
    public String update(Cart cart){
        service.update(cart);
        System.out.println(cart);
        return "修改成功返回的页面";
    }
    //4、删
    @DeleteMapping("delete")
    public String delete(@RequestParam("id") String cartId, HttpServletRequest request){
        ResultCodeEnum res = service.delete(cartId);
        if (res.getCode() == 200){
            return "返回成功的页面";
        }else{
            //删除失败打包返回数据
            //有点小问题===================================================
          request.setAttribute("deleMsg",request.getSession());
          return "返回失败的页面数据";
        }
    }

}
