package com.yiran.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiran.client.msm.MsmClient;
import com.yiran.common.result.R;
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
    private final MsmClient msmClient;
    public UserServiceImpl(UserMapper userMapper, MsmClient msmClient) {
        this.userMapper = userMapper;
        this.msmClient = msmClient;
    }

    /**
     * 构造器注入，不用@Autoweir
     * @param  userName 用户名字
     */

    @Override
    public User login(String userName, String password) {
        // 账户登录
        QueryWrapper<User> queryWrapperAccount = new QueryWrapper<>();
        queryWrapperAccount.eq("username",userName);
        queryWrapperAccount.eq("password",password);
        User userAccount = userMapper.selectOne(queryWrapperAccount);
        // 手机号登录
        QueryWrapper<User> queryWrapperPhone = new QueryWrapper<>();
        queryWrapperPhone.eq("phone",userName);
        queryWrapperPhone.eq("password",password);
        User userPhone = userMapper.selectOne(queryWrapperPhone);
        return userAccount == null ? userPhone : userAccount;
    }
    /**
     * 用户登录发放token
     * @param phoneNum 手机号码
     * @return token
     */
    @Override
    public User phoneLogin(String phoneNum) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phoneNum);
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

    /**
     * 点击注册，验证验证码是否一致是否过期，并将手机号码和密码存到数据库里面
     * @param phoneNum 用户手机号码
     * @param password 用户密码
     * @param message 验证码
     * @return boolean
     */
    @Override
    public boolean register(String phoneNum, String password, String message) {
        User user = new User();
        user.setPassword(password);
        user.setPhone(phoneNum);
        //查看数据库里该手机号是否被注册
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phoneNum);
        User userAlready = userMapper.selectOne(wrapper);
        if(userAlready == null){
            R<Boolean> response = msmClient.getMsg(phoneNum, message);
            int insert = 0;
            if(response.getCode() == 200){
                insert = userMapper.insert(user);
            }
            return insert > 0;
        }
       return false;
    }
}
