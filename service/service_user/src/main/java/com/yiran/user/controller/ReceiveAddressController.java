package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.ReceiveAddress;
import com.yiran.user.service.IReceiveAddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * @author weiyuwen
 */
@RestController
@RequestMapping("/receiveAddress")
public class ReceiveAddressController{
    private final IReceiveAddressService receiveAddressService;


    public ReceiveAddressController(IReceiveAddressService receiveAddressService) {
        this.receiveAddressService = receiveAddressService;
    }

    /**
     * 根据地址id查找地址
     * @return R
     */
    @RequestMapping("/getAddress")
    public R<ReceiveAddress> getAddress(){
        return R.ok("getAddressById",receiveAddressService.selectUserAddress("1001"));
    }

    /**
     * 根据用户id增加地址
     * @return R
     */
    @PostMapping("/insert")
    public R<Boolean> insertAddress(){
        return receiveAddressService.insertAddressByUserId("101")?R.ok("insert ok", true):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 根据地址id删除地址，is_delete=0
     * @return R
     */
    @PostMapping("/delete")
    public R<Boolean> deleteAddress(){
        return receiveAddressService.deleteAddress("1004")?R.ok("delete ok",true):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 更改用户信息
     * @param addressId 用户id
     * @return boolean
     */
    @PostMapping("/update")
    public R<Boolean> updateAddressByUserId(@PathParam("userId") String addressId){
        return receiveAddressService.updateAddress(addressId)?R.ok("update ok",true):R.fail(ResultCodeEnum.FAIL);
    }
    @GetMapping("/getUserAddress")
    public R<List<ReceiveAddress>> getUserAddress(@PathParam("userId") String userId){
        return R.ok("userAddressList",receiveAddressService.getAddressByUserId(userId));
    }
}
