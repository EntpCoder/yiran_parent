package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.ReceiveAddress;
import com.yiran.user.service.IReceiveAddressService;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getAddress")
    public R<ReceiveAddress> getAddress(@RequestHeader("userId") String userId,
                                        @RequestParam("addressId") String addressId){
        ReceiveAddress receiveAddress=receiveAddressService.selectAddress(userId,addressId);
        return receiveAddress==null?R.fail(ResultCodeEnum.FAIL):R.ok("ok",receiveAddress);
    }

    /**
     * 根据用户id增加地址
     * @return R
     */
    @PostMapping("/insert")
    public R<Boolean> insertAddress(@RequestHeader("userId") String userId){
        return receiveAddressService.insertAddressByUserId(userId)?R.ok("insert ok", true):R.fail(ResultCodeEnum.FAIL);
    }

    /**
     * 根据地址id删除地址，is_delete=0
     * @return R
     */
    @PostMapping("/delete")
    public R<Boolean> deleteAddress(@PathParam("addressId") String addressId){
        return receiveAddressService.deleteAddress(addressId)?R.ok("delete ok",true):R.fail(ResultCodeEnum.FAIL);
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

    /**
     * 根据用户id查找用户的所有地址
     * @param userId 用户id
     * @return 地址列表
     */
    @GetMapping("/getUserAddress")
    public R<List<ReceiveAddress>> getUserAddress(@RequestHeader("userId") String userId){
        List<ReceiveAddress> receiveAddresses=receiveAddressService.getAddressByUserId(userId);
        return receiveAddresses==null?R.fail(ResultCodeEnum.FAIL):R.ok("userAddressList",receiveAddresses);
    }
}
