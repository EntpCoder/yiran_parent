package com.yiran.user.service;

import com.yiran.model.entity.ReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param id 地址id
     * @return 地址详情
     */
    ReceiveAddress selectUserAddress(String id);

    /**
     * 按照用户id添加新地址
     * @param userId 用户id
     * @return 判断是否添加成功
     */
    Boolean insertAddressByUser(String userId);
}
