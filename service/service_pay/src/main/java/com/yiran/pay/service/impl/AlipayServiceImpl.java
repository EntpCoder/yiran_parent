package com.yiran.pay.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yiran.client.order.OrderClient;
import com.yiran.model.entity.Orders;
import com.yiran.model.vo.AlipayVo;
import com.yiran.pay.config.AlipayProperties;
import com.yiran.pay.service.IAlipayService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yang Song
 * @date 2022/11/3 9:28
 */
@Service
public class AlipayServiceImpl implements IAlipayService {
    private final DefaultAlipayClient client;
    private final OrderClient orderClient;
    private final RabbitTemplate rabbitTemplate;
    private final AlipayProperties alipayProperties;

    public AlipayServiceImpl(DefaultAlipayClient client, OrderClient orderClient,RabbitTemplate rabbitTemplate,AlipayProperties alipayProperties) {
        this.client = client;
        this.orderClient = orderClient;
        this.rabbitTemplate = rabbitTemplate;
        this.alipayProperties = alipayProperties;
    }

    @Override
    public String goAliPay(String orderId) {
        // 查询订单信息
        Orders order = orderClient.queryOrder(orderId).getData().get("order");
        // 订单超时时间
        String timeExpire = order.getCreateTime().plusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 调用alipay.trade.page.pay接口 发起支付请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayProperties.getReturnUrl());
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        // 必选四项参数 --其他参数参考官方文档
        bizContent.put("out_trade_no", order.getOrderId());
        bizContent.put("total_amount", order.getOrderAmount());
        bizContent.put("subject",order.getSubject());
        bizContent.put("time_expire",timeExpire);
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
        // 验签
        if (!isSign(params)){
            // 实际业务中需要对通知中的内容进行二次校验
            System.out.println("[验签失败]:订单Id" + alipayVo.getOut_trade_no());
            // 只要给支付宝返回的不是success这7个字符支付宝就会再次发送通知
            return "failure";
        }
        System.out.println("验签成功--发送消息");
        rabbitTemplate.convertAndSend("yiran.order.topic","order.pay.finish",alipayVo);
        return "success";
    }

    @Override
    public boolean isSign(Map<String, String[]> requestParams) {
        // 将Map<String, String[]> 转为 Map<String, String> 多个数据用","进行拼接
        Map<String, String> params = requestParams.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        (entry) -> String.join(",", entry.getValue())));
        boolean signVerified = false;
        try {
            // 使用支付宝SDK验签
            signVerified = AlipaySignature.verifyV1(params, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return signVerified;
    }
}
