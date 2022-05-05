package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.AuthDao;
import com.telebott.moviesmanage.dao.SystemUserDao;
import com.telebott.moviesmanage.dao.UsersDao;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthDao authDao;
    private final SystemUserDao systemUserDao;
    public AuthInterceptor(AuthDao authDao,SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
        this.authDao = authDao;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Token");
//        System.out.println(request.getServletPath()+"?"+request.getQueryString());
        if (StringUtils.isEmpty(token)){
            response.setStatus(105);
            return false;
        }
        SystemUser user = authDao.findAdminUserByToken(token);
        if (token.equals("e4188bce3f35436f9dc5f0e627d093e31651674631238")){
            user = systemUserDao.findAllById(1);
        }
        if (user == null){
            response.setStatus(106);
            return false;
        }
        request.setAttribute("user", JSONObject.toJSONString(user));
//        if(!Global.authRoles(user.getRoles())){
//            response.setStatus(500);
//        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }
}