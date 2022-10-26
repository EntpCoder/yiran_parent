package com.yiran.user.controller;

import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.User;
import com.yiran.user.service.IUserService;
import org.springframework.web.bind.annotation.*;



/**
 * @author weiyuwen
 * 实现
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    /**
     * 使用构造器注入
     * @param userService 服务类的方法
     */
    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/redister")
    public R<Boolean> redister(User user){
        return userService.redister(user)? R.ok("redister",true):R.fail(ResultCodeEnum.FAIL);
    }

    @DeleteMapping("/delete/{userId}")
    public R<Boolean> delete(@PathVariable("userId") String userId){
        return userService.delete(userId)?R.ok("delete",true):R.fail(ResultCodeEnum.FAIL);
    }

    @PutMapping("/update/{userId}")
    public R<Boolean> update(@PathVariable("userId") String userId){
        return userService.update(userId)?R.ok("update",true):R.fail(ResultCodeEnum.FAIL);
    }

    @GetMapping("/user")
    public User getUser(){
        return userService.selectUser();
    }
}
