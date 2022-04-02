package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawControl {
    @Autowired
    private WithdrawService service;
    @GetMapping("/getList")
    public ResultData getList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getList(requestData.getData()));
        return data;
    }
    @PostMapping("/handleShenHe")
    public ResultData handleShenHe(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.handleShenHe(requestData.getData(), user)){
            data.setCode(201);
            data.setMessage("审核失败！");
        }
        return data;
    }
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.update(requestData.getData(), user)){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @PostMapping("/handleBack")
    public ResultData handleBack(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.handleBack(requestData.getData())){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @PostMapping("/del")
    public ResultData del(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.del(requestData.getData(),systemUser)){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }

    @GetMapping("/getCardList")
    public ResultData getCardList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getCardList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateCard")
    public ResultData updateCard(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateCard(requestData.getData(), systemUser)){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @PostMapping("/delCard")
    public ResultData delCard(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.delCard(requestData.getData(),systemUser)){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
}
