package com.yiran.user.service;
import com.yiran.model.entity.User;

/**
 * @author weiyuwen
 */
public interface IUserService {
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
     * @return Users
     */
    User selectUser();

}
