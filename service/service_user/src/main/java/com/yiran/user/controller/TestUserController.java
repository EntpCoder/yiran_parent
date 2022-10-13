package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.model.entity.Users;
import com.yiran.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 11
 * 测试类
 */
@RestController
@RequestMapping("/user")
public class TestUserController {
    private  IUserService iUserService;
    @GetMapping("/user/{userId}")
    public R<List<Users>> selectUser(@PathVariable("userId") String userId){
        List<Users> list=iUserService.selectUser(userId);
        return list==null?R.fail():R.ok("userList",list);
    }
}
