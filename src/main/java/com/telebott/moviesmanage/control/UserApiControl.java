package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.LoginData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.SystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiControl {
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
}
