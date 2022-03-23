package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.sun.tracing.dtrace.Attributes;
import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.LoginData;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.SystemConfigService;
import com.telebott.moviesmanage.service.SystemUserService;
import com.telebott.moviesmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiControl {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private UserService userService;
    @GetMapping("/test")
    public ResultData test(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(systemUserService.getPasswdTest());
        return data;
    }
    @PostMapping("/login")
    public ResultData login(@RequestBody LoginData loginData){
        ResultData data = new ResultData();
        SystemUser systemUser = loginData.getUser();
        if (systemUser == null){
            systemUser = systemUserService.login(loginData);
        }
        if (systemUser == null){
            data.setCode(201);
            data.setMessage("用户不存在或者密码错误！");
        }else {
            if (systemUser.getStatus() == 1){
                data.setData(systemUserService.getLoginResult(systemUser));
            }else {
                data.setCode(201);
                data.setMessage("用户已被禁用，请联系管理员!");
            }
        }
        return data;
    }
}
