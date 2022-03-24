package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.*;
import com.telebott.moviesmanage.service.SystemConfigService;
import com.telebott.moviesmanage.service.SystemUserService;
import com.telebott.moviesmanage.service.UserService;
import com.telebott.moviesmanage.service.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class ApiControl {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private VideosService videosService;
    @GetMapping("/test")
    public ResultData test(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(systemUserService.getPasswdTest());
        return data;
    }
    private String getJsonBodyString(HttpServletRequest httpServletRequest){
        try {
            httpServletRequest.setCharacterEncoding("UTF-8");
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader=null;
            reader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), StandardCharsets.UTF_8));
            String line=null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
//            System.out.println(buffer);
            return buffer.toString();
//            InputStream inputStream = httpServletRequest.getInputStream();
//            StringBuilder stringBuilder = new StringBuilder();
//            int temp;
//            while ((temp = inputStream.read()) != -1)
//            {
//                stringBuilder.append((char) temp);
//            }
//            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/Yzm")
    public  ResultData Yzm(HttpServletRequest httpServletRequest){
        ResultData data = new ResultData();
        String jsonStr = getJsonBodyString(httpServletRequest);
        if (jsonStr != null){
            JSONObject object = JSONObject.parseObject(jsonStr);
            if (object != null){
                YzmData yzmData = JSONObject.toJavaObject(object,YzmData.class);
                if (yzmData != null && yzmData.getResult().equals("ok")){
                    videosService.handlerYzm(yzmData);
                }
            }
        }
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
