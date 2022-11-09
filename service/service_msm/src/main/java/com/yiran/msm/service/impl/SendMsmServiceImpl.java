package com.yiran.msm.service.impl;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yiran.msm.service.SendMsmService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;

/**
 * @author 庆瑞瑞
 * @date 2022/11/7 10:13
 */
@Service
@Data
public class SendMsmServiceImpl implements SendMsmService {
    private static final Logger logger = LoggerFactory.getLogger(SendMsmServiceImpl.class);
    private final RedisTemplate<String,Object> redisTemplate;

    public SendMsmServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * //aliyuncs的参数
     */
    @Value("${aliyun.accessKeyID}")
    private String accessKeyID;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    /**
     *  //短信api的请求地址  固定
     */
    @Value("${aliyun.domain}")
    private String domain;
    /**
     * //指定地域名称 短信API的就是 cn-hangzhou 不能改变
     */
    @Value("${aliyun.regionId}")
    private String regionId;
    /**
     * //您的申请签名
     */
    @Value("${aliyun.signName}")
    private String signName;
    /**
     * //你的模板
     */
    @Value("${aliyun.templateCode}")
    private String templateCode;

    @Override
    public Boolean sendMsm(String phoneNum) {
        //如果手机号码为11位
        if(phoneNum.length() == 11){
            // 指定地域名称 短信API的就是 cn-hangzhou 不能改变  后边填写您的  accessKey 和 accessKey Secret
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);
            // 创建通用的请求对象
            CommonRequest request = new CommonRequest();
            // 指定请求方式
            request.setSysMethod(MethodType.POST);
            // 短信api的请求地址  固定
            request.setSysDomain(domain);
            //签名算法版本  固定
            request.setSysVersion("2017-05-25");
            //请求 API 的名称
            request.setSysAction("SendSms");
            //指定地域名称
            request.putQueryParameter("RegionId", regionId);
            // 要给哪个手机号发送短信  指定手机号
            request.putQueryParameter("PhoneNumbers", phoneNum);
            // 您的申请签名
            request.putQueryParameter("SignName", signName);
            // 您申请的模板 code
            request.putQueryParameter("TemplateCode", templateCode);
            Map<String, Object> params = new HashMap<>(2);
            //这里的key就是短信模板中的 ${xxxx}
            // 随机生成四位随机数
            String message = "";
            for (int x = 0; x <= 3; x++) {
                int random = (int) (Math.random() * (10 - 1));
                message+=random;
            }
            params.put("code", message);

            // 放入参数  需要把 map转换为json格式  使用fastJson进行转换
            request.putQueryParameter("TemplateParam", JSON.toJSONString(params));

            try {
                //判断redis里面是否有key为phoneNum的键值对，如果没有就生成并存到redis里面
                String aa = (String)redisTemplate.opsForValue().get(phoneNum);
                if(aa == null){
                    //发送验证码
                    CommonResponse response = client.getCommonResponse(request);
                    logger.info(JSON.parseObject(response.getData(), Map.class).get("Message").toString());
                    //将生成的message放进redis里面，五分钟，key为message，value为message_时间戳
                    redisTemplate.opsForValue().set(phoneNum,message+"_"+ (new Date()).getTime(),5, TimeUnit.MINUTES);
                    System.out.println("生成的验证码为："+message+"放进redis里面");
                    return response.getHttpResponse().isSuccess();
                }else{
                    //redis里面含有建为phoneNum的键值对，判断存入redis时间戳距离现在是否超过一分钟
                    LocalDateTime now = LocalDateTime.now();
                    long l = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                    String[] s = aa.split("_");
                    long start = Long.parseLong(s[1]);
                    long second = (l - start)/1000;
                    if(second > 60){
                        //可以发送验证码
                        CommonResponse response = client.getCommonResponse(request);
                        logger.info(JSON.parseObject(response.getData(), Map.class).get("Message").toString());
                        //将生成的message放进redis里面，五分钟，key为message，value为message_时间戳
                        redisTemplate.opsForValue().set(phoneNum,message+"_"+ (new Date()).getTime(),5, TimeUnit.MINUTES);
                        System.out.println("大于60s，再次生成的验证码为："+message+"放进redis里面");
                        return response.getHttpResponse().isSuccess();
                    }
                    else{
                        System.out.println("获取验证码的时间小于60s，获取失败");
                        return false;
                    }
                }
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;

    }

    /**
     *  //五分钟之内点击登录获取redis里面的值，判断验证码是否和redis获取到的一样否则获取不到
     * @param phoneNum 手机号
     * @param code 获取到的验证码
     * @return 验证码校验是否成功
     */
    @Override
    public Boolean getMsg(String phoneNum,String code) {
        //从redis里面获取phoneNum键的值
        String msg = (String)redisTemplate.opsForValue().get(phoneNum);
        String message = "";
        //msg验证码不为空，说明没失效
        if(msg != null){
            System.out.println("验证码不为空**************");
            message += msg.split("_")[0];
            System.out.println(message+"为验证码：**********************************");
            if(message.equals(code)){
                System.out.println("验证码和redis一致...");
                return true;
            }
            //输入的验证码和redis里面的验证码不一致
            System.out.println("验证码不一致"+"redis获取的验证码："+message+"输入的的验证吗："+code);
            return false;
        }
        System.out.println("redis验证码已过期");
        return false;
    }
}
