package com.yiran.msm.controller;

import com.yiran.common.result.R;
import com.yiran.msm.service.SendMsmService;
import org.springframework.web.bind.annotation.*;

/**
 * @author 庆瑞瑞
 * @date 2022/11/7 10:53
 */
@RestController
@RequestMapping("/msm")
public class MsmController {
    private final SendMsmService sendMsmService;

    public MsmController(SendMsmService sendMsmService) {
        this.sendMsmService = sendMsmService;
    }

    /**
     * 发送验证码
     * @param phoneNum 手机号码
     * @return Boolean
     */
    @GetMapping("/sendMsm")
    public R<Boolean> sendMsm(@RequestParam("phoneNum") String phoneNum){
        Boolean sendMsm = sendMsmService.sendMsm(phoneNum);
        return sendMsm ? R.ok("result", true) : R.fail();
    }
    @GetMapping("/getMsg")
    public  R<Boolean> getMsg(@RequestParam("phoneNum") String phoneNum,@RequestParam("code") String code){
        Boolean getMsg = sendMsmService.getMsg(phoneNum, code);
        return getMsg ? R.ok("result", true) : R.fail();
    }
}
