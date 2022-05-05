package com.telebott.moviesmanage.dao;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telebott.moviesmanage.entity.SmsCode;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@Repository
public class AuthDao {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private SystemUserDao systemUserDao;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Timer timer = new Timer();
    public void pushCode(SmsCode smsCode){
        SmsCode object = findCode(smsCode.getId());
        if (object != null){
            popCode(object);
        }
//        redisTemplate.opsForSet().add("smsCode",JSONObject.toJSONString(smsCode),5, TimeUnit.MINUTES);
        redisTemplate.opsForSet().add("smsCode",JSONObject.toJSONString(smsCode));
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                popCode(smsCode);
            }
        }, 1000 * 60 * 5);
    }
    public SmsCode findCode(String id){
        Set smsCode = redisTemplate.opsForSet().members("smsCode");
        if (smsCode != null){
            JSONObject jsonObject = new JSONObject();
            for (Object code: smsCode) {
                jsonObject = JSONObject.parseObject(code.toString());
                if (id.equals(jsonObject.get("id"))){
                    return JSONObject.toJavaObject(jsonObject,SmsCode.class);
                }
            }
        }
        return null;
    }
    public void removeByPhone(String phone){
        Set smsCode = redisTemplate.opsForSet().members("smsCode");
        if (smsCode != null){
            JSONObject jsonObject = new JSONObject();
            for (Object code: smsCode) {
                jsonObject = JSONObject.parseObject(code.toString());
                if (phone.equals(jsonObject.get("phone"))){
                    popCode(JSONObject.toJavaObject(jsonObject,SmsCode.class));
                }
            }
        }
    }
    public SmsCode findByPhone(String phone){
        Set smsCode = redisTemplate.opsForSet().members("smsCode");
        if (smsCode != null){
            JSONObject jsonObject = new JSONObject();
            for (Object code: smsCode) {
                jsonObject = JSONObject.parseObject(code.toString());
                if (phone.equals(jsonObject.get("phone"))){
                    return (JSONObject.toJavaObject(jsonObject,SmsCode.class));
                }
            }
        }
        return null;
    }
    public void popCode(SmsCode code){
        redisTemplate.opsForSet().remove("smsCode" ,JSONObject.toJSONString(code));
    }
    public void pushUser(Users userToken){
        if (StringUtils.isNotEmpty(userToken.getToken())) {
            Set users = redisTemplate.opsForSet().members("Users");
            assert users != null;
//        System.out.println(users.toString());
            for (Object user: users) {
                ObjectMapper objectMapper = new ObjectMapper();
                Users userEntity = objectMapper.convertValue(user, Users.class);
                if (userEntity.getId() == userToken.getId()){
                    popUser(userEntity);
                }
            }
            redisTemplate.opsForSet().add("Users",userToken);
        }
    }
    public void updateUser(String token){
        Users user = findUserByToken(token);
        if (user != null){
            if (user.getId() > 0){
                user = usersDao.findAllById(user.getId());
                if (user != null){
                    user.setToken(token);
                    pushUser(user);
                }
            }else {
                pushUser(user);
            }
        }
    }
    public void removeUser(Users userToken){
        Set users = redisTemplate.opsForSet().members("Users");
        if (users != null){
            for (Object user: users) {
                ObjectMapper objectMapper = new ObjectMapper();
                Users userEntity = objectMapper.convertValue(user,Users.class);
                if (userEntity.getId() == userToken.getId()){
                    popUser(userEntity);
                }
            }
        }
    }
    public void popUser(Users userToken){
        redisTemplate.opsForSet().remove("Users" ,userToken);
    }
    //获取RedisTemplate 中key 的总数
    public long countAllUser(){
        long count = 0;
        Set users = redisTemplate.opsForSet().members("Users");
        for (Object o : users) {
            count++;
        }
        return count;
    }
    public Users findUserByIdentifier(String id) {
        Set users = redisTemplate.opsForSet().members("Users");
        if (users != null){
            for (Object user: users) {
                ObjectMapper objectMapper = new ObjectMapper();
                Users userEntity = objectMapper.convertValue(user,Users.class);
                if (userEntity.getIdentifier().equals(id)){
                    return userEntity;
                }
            }
        }
        return null;
    }
    public Users findUserByToken(String token) {
        Set users = redisTemplate.opsForSet().members("Users");
        if (users != null){
            for (Object user: users) {
                ObjectMapper objectMapper = new ObjectMapper();
                Users userEntity = objectMapper.convertValue(user,Users.class);
                if (userEntity.getToken().equals(token)){
                    return userEntity;
                }
            }
        }
        return null;
    }
    public void pushAdminUser(SystemUser userToken){
        if (StringUtils.isNotEmpty(userToken.getToken())) {
            Set AdminUsers = redisTemplate.opsForSet().members("AdminUser");
            assert AdminUsers != null;
//        System.out.println(users.toString());
            for (Object user: AdminUsers) {
                ObjectMapper objectMapper = new ObjectMapper();
                SystemUser AdminUser = objectMapper.convertValue(user, SystemUser.class);
                if (AdminUser.getToken().equals(userToken.getToken()) || AdminUser.getId() == userToken.getId()){
                    popAdminUser(AdminUser);
                }
            }
            redisTemplate.opsForSet().add("AdminUser",userToken);
        }
    }
    public void updateAdminUser(String token){
        SystemUser user = findAdminUserByToken(token);
        if (user != null){
            user = systemUserDao.findAllById(user.getId());
            if (user != null){
                user.setToken(token);
                pushAdminUser(user);
            }
        }
    }
    public void removeAdminUser(SystemUser userToken){
//        Set users = redisTemplate.opsForSet().members("AdminUser");
//        if (users != null){
//            for (Object user: users) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                SystemUser userEntity = objectMapper.convertValue(user,SystemUser.class);
//                if (userEntity.getToken().equals(userToken.getToken())){
//                    popAdminUser();
//                }
//            }
//        }
    }
    public void popAdminUser(SystemUser userToken){
        redisTemplate.opsForSet().remove("AdminUser" ,userToken);
    }
    public SystemUser findAdminUserByToken(String token) {
        Set users = redisTemplate.opsForSet().members("AdminUser");
        if (users != null){
            for (Object user: users) {
                ObjectMapper objectMapper = new ObjectMapper();
                SystemUser userEntity = objectMapper.convertValue(user,SystemUser.class);
                if (userEntity.getToken().equals(token)){
                    return userEntity;
                }
            }
        }
        return null;
    }

    public Set getAllUser(){
        return redisTemplate.opsForSet().members("Users");
    }
}
