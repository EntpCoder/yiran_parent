package com.yiran.user.service;

import com.yiran.model.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> selectUser(String userId);
}
