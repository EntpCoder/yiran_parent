package com.yiran.user.controller;

import com.yiran.common.JwtUtil;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.model.entity.User;
import com.yiran.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


/**
 * @author weiyuwen
 * 实现
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    /**
     * 使用构造器注入
     * @param userService 服务类的方法
     */
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/accountLogin")
    public R<String> login(String userName,String password){
        User user = userService.login(userName,password);
        return user == null ? R.fail(ResultCodeEnum.LOGIN_FAIL) : R.ok("token",JwtUtil.sign(user));
    }
    @PostMapping("/phoneLogin")
    public R<String> phoneLogin(String phoneNum){
        User user = userService.phoneLogin(phoneNum);
        return user == null ? R.fail(ResultCodeEnum.LOGIN_FAIL) : R.ok("token",JwtUtil.sign(user));
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
    public R<User> getUser(@PathParam("userId") String userId){
        return R.ok("get userMessage",userService.selectUser(userId));
    }
    @GetMapping("/register")
    public R<Boolean> register(@RequestParam("phoneNum")String phoneNum,@RequestParam("password")String password,@RequestParam("message")String message){
        boolean b = userService.register(phoneNum, password, message);
        return b ? R.ok("result",true):R.fail();
    }
}
