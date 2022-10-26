package com.yiran.user.service.impl;

import com.yiran.model.entity.ReceiveAddress;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.user.mapper.ReceiveAddressMapper;
import com.yiran.user.service.IReceiveAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Service
public class ReceiveAddressServiceImpl extends ServiceImpl<ReceiveAddressMapper, ReceiveAddress> implements IReceiveAddressService {
    public ReceiveAddressServiceImpl(ReceiveAddressMapper receiveAddressMapper) {
        this.receiveAddressMapper = receiveAddressMapper;
    }

    private final ReceiveAddressMapper receiveAddressMapper;
    @Override
    public ReceiveAddress selectUserAddress(String id) {
        return receiveAddressMapper.selectById(id);
    }

    @Override
    public Boolean insertAddressByUser(String userId) {
        ReceiveAddress receiveAddress=new ReceiveAddress();
        receiveAddress.setReceiveId("1003");
        receiveAddress.setName("");
        return null;
    }
}
