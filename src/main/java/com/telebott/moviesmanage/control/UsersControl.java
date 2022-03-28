package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/delete")
    public ResultData delete(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.delete(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！用户不存在");
        }
        return data;
    }
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.update(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！用户不存在");
        }
        return data;
    }
    @GetMapping("/superior")
    public ResultData superior(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.superior(requestData.getData()));
        return data;
    }
    @GetMapping("/share")
    public ResultData share(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.share(requestData.getData()));
        return data;
    }
    @GetMapping("/getSmsRecordsList")
    public ResultData getSmsRecordsList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.getSmsRecordsList(requestData.getData()));
        return data;
    }
}
