package com.yiran.user.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yiran.model.entity.User;

/**
 * @author weiyuwen
 */
public interface IUserService extends IService<User> {
    /**
     * 用户登录发放token
     * @param userName 用户名
     * @param password 用户密码
     * @return token
     */
    User login(String userName,String password);

    /**
     * 用户登录发放token
     * @param phoneNum 手机号码
     * @return token
     */
    User phoneLogin(String phoneNum);
    /**
     * 新增用户
     * @param user 用户实例
     * @return 加入的个数
     */
    boolean redister(User user);

    /**
     * 注销用户
     * @param userId 用户Id
     * @return 更改个数是否大于1
     */
    boolean delete(String userId);

    /**
     * 用户信息更新
     * @param userId 用户Id
     * @return 更改个数是否大于1
     */
    boolean update(String userId);

    /**
     *  查找用户
     * @param userId 用户id
     * @return 用户信息
     */
    User selectUser(String userId);

    /**
     * 点击注册，验证验证码是否一致是否过期，并将手机号码和密码存到数据库里面
     * @param phoneNum 用户手机号码
     * @param password 用户密码
     * @param message 验证码
     * @return boolean
     */
    boolean register(String phoneNum,String password,String message);

}
