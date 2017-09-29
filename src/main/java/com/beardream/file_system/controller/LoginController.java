package com.beardream.file_system.controller;

import com.beardream.file_system.model.Result;
import com.beardream.file_system.model.User;
import com.beardream.file_system.service.UserService;
import com.beardream.file_system.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService mUserService;

    @GetMapping
    private Result login(User user){
        if(user.getUsername() == null || user.getPassword() == null)
            return ResultUtil.error(-1,"用户名或密码不能为空");

        return mUserService.login(user);
    }

    @DeleteMapping
    private Result logout(User user) {
        return mUserService.logout(user);
    }
}
