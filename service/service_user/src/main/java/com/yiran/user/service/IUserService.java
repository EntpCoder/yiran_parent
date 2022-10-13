package com.yiran.user.service;

import com.yiran.model.entity.Users;
import org.apache.catalina.User;

import java.util.List;

public interface IUserService {
    public List<Users> selectUser(String userId);
}
