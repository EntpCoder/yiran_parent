package com.yiran.pay.controller;

import com.yiran.pay.service.IAlipayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Song
 * @date 2022/11/3 9:20
 */
@RestController
@RequestMapping("/pay")
public class AliPayController {
    private final IAlipayService alipayService;
    public AliPayController(IAlipayService iAlipayService){
        this.alipayService = iAlipayService;
    }
    /**
     * 根据订单id 拉起支付页面
     * @param orderId 订单id
     * @return 支付页面
     */
    @GetMapping("/goAliPay/{orderId}")
    public String goPay(@PathVariable("orderId") String orderId){
        return alipayService.goAliPay(orderId);
    }
}
