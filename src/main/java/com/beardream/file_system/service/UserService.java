package com.beardream.file_system.service;

import com.beardream.file_system.dao.UserDao;
import com.beardream.file_system.inter.TokenManager;
import com.beardream.file_system.model.Result;
import com.beardream.file_system.model.Token;
import com.beardream.file_system.model.User;
import com.beardream.file_system.util.MD5;
import com.beardream.file_system.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserDao mUserDao;

    @Autowired
    private TokenManager mTokenManager;

    public Result login(User user) {

        String pwd = MD5.GetMD5Code(user.getPassword());
        user.setPassword(pwd);
        User u = mUserDao.login(user);
        if (u == null) return ResultUtil.error(-1,"用户名或密码错误");
        // 生成token
        Token token = mTokenManager.createToken(user.getUserId());
        return ResultUtil.success(token);
    }

    public Result logout(User user) {
        User u = mUserDao.selectByPrimaryKey(user.getUserId());
        if (u == null)
            return ResultUtil.error(-1,"用户不存在");
        mTokenManager.deleteToken(user.getUserId());
        return ResultUtil.success("注销成功");
    }
}
