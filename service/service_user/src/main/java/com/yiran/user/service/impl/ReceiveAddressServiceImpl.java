package com.yiran.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiran.model.entity.ReceiveAddress;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.entity.User;
import com.yiran.user.mapper.ReceiveAddressMapper;
import com.yiran.user.mapper.UserMapper;
import com.yiran.user.service.IReceiveAddressService;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private final ReceiveAddressMapper receiveAddressMapper;
    private final UserMapper userMapper;

    public ReceiveAddressServiceImpl(ReceiveAddressMapper receiveAddressMapper,UserMapper userMapper) {
        this.receiveAddressMapper = receiveAddressMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ReceiveAddress selectAddress(String userId,String addressId) {
        QueryWrapper<ReceiveAddress> receiveAddressQueryWrapper = new QueryWrapper<>();
        receiveAddressQueryWrapper.eq("receive_id",addressId);
        receiveAddressQueryWrapper.eq("user_id",userId);
        return receiveAddressMapper.selectOne(receiveAddressQueryWrapper);
    }

    @Override
    public Boolean insertAddressByUserId(String userId) {
        ReceiveAddress receiveAddress=new ReceiveAddress();
        receiveAddress.setReceiveId("1003");
        receiveAddress.setName("ccc");
        receiveAddress.setPhone("19245632596");
        receiveAddress.setProvince("广东省");
        receiveAddress.setCity("深圳市");
        receiveAddress.setArea("龙华区");
        receiveAddress.setStree("大浪街道");
        receiveAddress.setUserId(userId);
        int row=receiveAddressMapper.insert(receiveAddress);
        return row>0;
    }

    @Override
    public Boolean deleteAddress(String addressId) {
        ReceiveAddress address=receiveAddressMapper.selectById(addressId);
        address.setIsDelete(false);
        int row=receiveAddressMapper.updateById(address);
        return row>0;
    }

    @Override
    public Boolean updateAddress(String addressId) {
        Byte time=0;
        Byte type=0;
        ReceiveAddress address=receiveAddressMapper.selectById(addressId);
        address.setName("小美");
        address.setPhone("12364885461");
        address.setTime(time);
        address.setProvince("");
        address.setCity("翻斗市");
        address.setArea("翻斗区");
        address.setStree("翻斗大街");
        address.setDetail("");
        address.setType(type);
        int row=receiveAddressMapper.updateById(address);
        return row>0;
    }

    @Override
    public List<ReceiveAddress> getAddressByUserId(String userId) {
        // 查找用户的所有地址
        QueryWrapper <ReceiveAddress> addressQueryWrapper=new QueryWrapper<>();
        addressQueryWrapper.eq("user_id",userId);
        List<ReceiveAddress> addressList=receiveAddressMapper.selectList(addressQueryWrapper);
        User user = userMapper.selectById(userId);
        // 标识用户默认地址
        addressList.stream()
                .filter(r->r.getReceiveId().equals(user.getDefaultAddressId()))
                .forEach(r -> r.setDefault(true));
        return addressList;
    }


}
