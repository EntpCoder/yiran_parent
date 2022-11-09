package com.yiran.msm.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author 庆瑞瑞
 * @date 2022/11/7 10:12
 */
public interface SendMsmService {
    /**
     * 发送短信的接口
     * @param phoneNum 手机号
     * @return 验证码是否成功
     */
    Boolean sendMsm(String phoneNum);

    /**
     * 五分钟之内输入验证码并点击登录，否则验证码失效，获取不到,判断获取从redis获取到的验证码是否和前端传来的一样
     * @param phoneNum 手机号
     * @return 校验验证码
     */
    Boolean getMsg(String phoneNum, String code);
}
