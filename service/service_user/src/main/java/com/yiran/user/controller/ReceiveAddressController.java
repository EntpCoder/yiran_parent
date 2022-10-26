package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.model.entity.ReceiveAddress;
import com.yiran.user.service.IReceiveAddressService;
import com.yiran.user.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiyuwen
 */
@RestController
@RequestMapping("/receiveAddress")
public class ReceiveAddressController{
    private final IReceiveAddressService receiveAddressService;
    private final IUserService userService;


    public ReceiveAddressController(IReceiveAddressService receiveAddressService, IUserService userService) {
        this.receiveAddressService = receiveAddressService;
        this.userService = userService;
    }

    @RequestMapping("/getAddress")
    public ReceiveAddress getAddress(){
        return receiveAddressService.selectUserAddress("1001");
    }
}
