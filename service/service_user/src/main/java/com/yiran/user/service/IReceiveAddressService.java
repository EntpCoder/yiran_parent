package com.yiran.user.service;

import com.yiran.model.entity.ReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
public interface IReceiveAddressService extends IService<ReceiveAddress> {
    /**
     * 按地址id查找地址信息
     * @param addressId 地址id
     * @param userId 用户id
     * @return 地址详情
     */
    ReceiveAddress selectAddress(String userId,String addressId);

    /**
     * 按照用户id添加新地址
     * @param userId 用户id
     * @return 判断是否添加成功
     */
    Boolean insertAddressByUserId(String userId);

    /**
     * 根据地址id删除地址
     * @param addressId 地址id
     * @return Boolean
     */
    Boolean deleteAddress(String addressId);

    /**
     * 按照用户id更改地址信息
     * @param addressId 用户id
     * @return Boolean
     */
    Boolean updateAddress(String addressId);

    /**
     * 根据用户查找用户的所有地址
     * @param userId 用户id
     * @return 地址列表
     */
    List<ReceiveAddress> getAddressByUserId(String userId);
}
