package com.yiran.pay.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yiran.client.order.OrderClient;
import com.yiran.common.result.R;
import com.yiran.model.entity.Orders;
import com.yiran.model.vo.AlipayVo;
import com.yiran.pay.config.AlipayProperties;
import com.yiran.pay.service.IAlipayService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Yang Song
 * @date 2022/11/3 9:28
 */
@Service
public class AlipayServiceImpl implements IAlipayService {
    private final DefaultAlipayClient client;
    private final OrderClient orderClient;

    public AlipayServiceImpl(DefaultAlipayClient client, OrderClient orderClient) {
        this.client = client;
        this.orderClient = orderClient;
    }

    @Override
    public String goAliPay(String orderId) {
        // 查询订单信息
        Orders order = orderClient.queryOrder(orderId).getData().get("order");
        // 调用alipay.trade.page.pay接口 发起支付请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(AlipayProperties.RETURN_URL);
        request.setNotifyUrl(AlipayProperties.NOTIFY_URL);
        JSONObject bizContent = new JSONObject();
        // 必选四项参数 --其他参数参考官方文档
        bizContent.put("out_trade_no", order.getOrderId());
        bizContent.put("total_amount", order.getOrderAmount());
        bizContent.put("subject",order.getSubject());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        String form = "";
        try {
            form = client.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @Override
    public String callBackAsync(AlipayVo alipayVo, Map<String, String[]> params) {
        return null;
    }

    @Override
    public boolean isSign(Map<String, String[]> requestParams) {
        return false;
    }
}
