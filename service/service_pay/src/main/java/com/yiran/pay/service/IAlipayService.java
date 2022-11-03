package com.yiran.pay.service;

import com.yiran.model.vo.AlipayVo;

import java.util.Map;

/**
 * @author Yang Song
 * @date 2022/11/3 9:25
 */
public interface IAlipayService {
    /**
     *  拉起页面
     * @param orderId 待支付订单id
     * @return 页面
     */
    String goAliPay(String orderId);

    /**
     * 交易成功 异步回调
     * @param alipayVo 回调参数
     * @param params 验签参数
     * @return 返回给支付宝是否成功(success/failure)
     */
    String callBackAsync(AlipayVo alipayVo, Map<String,String[]> params);

    /**
     * 验签
     * @param requestParams 请求体参数
     * @return 验签是否成功
     */
    boolean isSign(Map<String, String[]> requestParams);
}
