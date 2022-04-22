package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.AuthDao;
import com.telebott.moviesmanage.dao.SystemUserDao;
import com.telebott.moviesmanage.entity.LoginData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SystemUserService {
    @Autowired
    private SystemUserDao systemUserDao;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthDao authDao;
    public SystemUser login(LoginData loginData) {
        if (StringUtils.isNotEmpty(loginData.getUsername()) && StringUtils.isNotEmpty(loginData.getPassword())){
            SystemUser user = systemUserDao.findAllByUsername(loginData.getUsername());
            if (user != null){
                MD5Util md5Util = new MD5Util(user.getSalt());
                String pass = md5Util.getPassWord(loginData.getPassword());
                if(pass.equals(user.getPassword())) return user;
            }
        }
        return null;
    }
    public JSONObject getLoginResult(SystemUser systemUser){
        JSONObject data = new JSONObject();
        if (StringUtils.isNotEmpty(systemUser.getToken())){
            data.put("token",systemUser.getToken());
        }else {
            systemUser.setToken(getToken());
            authDao.pushAdminUser(systemUser);
            data.put("token",systemUser.getToken());
        }
        return data;
    }
    public void logout(SystemUser systemUser){
        authDao.popAdminUser(systemUser);
    }
    public JSONObject getInfoResult(SystemUser systemUser) {
        JSONObject data = new JSONObject();
        JSONArray roles = new JSONArray();
        roles.add("admin");
        data.put("roles",roles);
        data.put("introduction",systemUser.getIntroduction());
        data.put("avatar",systemUser.getAvatar());
        data.put("name",systemUser.getName());
        return data;
    }
    private String getToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","")+System.currentTimeMillis();
    }
    public JSONObject getPasswdTest(){
        JSONObject object = new JSONObject();
        String salt = userService._getSalt();
        long time = System.currentTimeMillis();
        MD5Util md5Util = new MD5Util(salt);
        object.put("pass", md5Util.getPassWord(md5Util.getMD5("Admin@123")));
        object.put("salt", salt);
        object.put("time", time);
        return object;
    }
}
