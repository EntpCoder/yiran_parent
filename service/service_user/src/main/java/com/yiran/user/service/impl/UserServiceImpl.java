package com.yiran.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.model.entity.User;
import com.yiran.user.mapper.UserMapper;
import com.yiran.user.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * 方法类
 * @author  weiyuwen
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    private final UserMapper userMapper;

    /**
     * 构造器注入，不用@Autoweir
     * @param userMapper Users类的方法
     */
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper=userMapper;
    }
    @Override
    public User login(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        queryWrapper.eq("password",password);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 新增用户，用于用户注册
     * @param user 用户实例
     * @return 新增信息数量是否大于1
     */
    @Override
    public boolean redister(User user) {
        user=new User();
        user.setUserId("103");
        user.setOpenId("33333");
        user.setPhone("13526152523");
        user.setPassword("123aaa");
        user.setUsername("cindy");
        user.setGender(false);
        user.setIsDelete(false);
        int row=userMapper.insert(user);
        return row>0;
    }

    @Override
    public boolean delete(String userId) {
        int row=userMapper.deleteById(userId);
        return row>0;
    }

    @Override
    public boolean update(String userId) {
        User user=userMapper.selectById(userId);
        user.setUsername("Elina");
        user.setPassword("3652eee");
        int row=userMapper.updateById(user);
        return row>0;
    }

    /**
     * 按照id查找用户
     * @return Users
     */
    @Override
    public User selectUser(String userId) {
        return userMapper.selectById(userId);
    }
}
