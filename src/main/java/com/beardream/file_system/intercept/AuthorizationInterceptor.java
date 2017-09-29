package com.beardream.file_system.intercept;

import com.beardream.file_system.aop.Authorization;
import com.beardream.file_system.constants.Constants;
import com.beardream.file_system.inter.TokenManager;
import com.beardream.file_system.model.Token;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager mTokenManager;

    public boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        if (!(handler instanceof HandlerMethod))
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从head中拿到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        // 验证token
        Token token = mTokenManager.getToken(authorization);
        if (mTokenManager.checkToken(token)){
            request.setAttribute(Constants.USER, token.getUserId());
            return true;
        }
        if (method.getAnnotation(Authorization.class) != null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

}
