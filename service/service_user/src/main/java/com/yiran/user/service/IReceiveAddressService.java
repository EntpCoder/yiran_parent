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
    ReceiveAddress selectUserAddress(String id);
}
