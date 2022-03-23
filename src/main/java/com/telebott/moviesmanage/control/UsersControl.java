package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersControl {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResultData list(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.getList(requestData.getData()));
        return data;
    }
}
