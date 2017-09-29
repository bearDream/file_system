package com.beardream.file_system.impl;

import com.beardream.file_system.constants.Constants;
import com.beardream.file_system.inter.TokenManager;
import com.beardream.file_system.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenManagerImpl implements TokenManager {

    private RedisTemplate mRedis;

    @Autowired
    public void setRedis(@Qualifier("redisTemplate") RedisTemplate redis){
        this.mRedis = redis;
        mRedis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public Token createToken(int userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        Token model = new Token(userId, token);
        mRedis.boundValueOps(userId).set(token, Constants.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        return model;
    }

    @Override
    public boolean checkToken(Token model) {
        if (model == null)
            return false;
        String token = mRedis.boundValueOps(model.getUserId()).get().toString();
        if (token == null || !token.equals(model.getToken()))
            return false;
        mRedis.boundValueOps(model.getUserId()).expire(Constants.TOKEN_EXPIRE_TIME, TimeUnit.HOURS);
        return true;
    }

    @Override
    public Token getToken(String authenrization) {
        if (authenrization == null || authenrization.length() == 0)
            return null;
        String[] param = authenrization.split("_");
        if (param.length != 2)
            return null;
        int userId = Integer.parseInt(param[0]);
        String token = param[1];
        return new Token(userId, token);
    }

    @Override
    public void deleteToken(int userId) {
        mRedis.delete(userId);
    }
}
