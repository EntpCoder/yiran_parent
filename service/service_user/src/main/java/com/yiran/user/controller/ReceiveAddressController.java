package com.yiran.user.controller;

import com.yiran.model.entity.ReceiveAddress;
import com.yiran.user.service.IReceiveAddressService;
import com.yiran.user.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

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

    /**
     * 根据地址id查找地址
     * @return 地址详情
     */
    @RequestMapping("/getAddress")
    public ReceiveAddress getAddress(){
        return receiveAddressService.selectUserAddress("1001");
    }
    @PostMapping("/insert")
    public Boolean insertAddress(){
        return receiveAddressService.insertAddressByUser("101");
    }
}
