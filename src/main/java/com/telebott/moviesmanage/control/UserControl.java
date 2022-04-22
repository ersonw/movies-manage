package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.AuthDao;
import com.telebott.moviesmanage.entity.LoginData;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserControl {
    @Autowired
    private SystemUserService systemUserService;


    @GetMapping("/info")
    public ResultData info(@ModelAttribute LoginData loginData){
        ResultData data = new ResultData();
        if (loginData.getUser() != null){
            data.setData(systemUserService.getInfoResult(loginData.getUser()));
        }
        return data;
    }
    @PostMapping("/logout")
    public ResultData logout( @RequestAttribute String user){
//        System.out.println(user);
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        systemUserService.logout(systemUser);
        return data;
    }
}
