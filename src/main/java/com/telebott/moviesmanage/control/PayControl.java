package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pay")
public class PayControl {
    @Autowired
    private PayService service;
    @GetMapping("/getTypeList")
    public ResultData getTypeList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getTypeList(requestData.getData()));
        return data;
    }
    @GetMapping("/getOnlinePayList")
    public ResultData getOnlinePayList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getOnlinePayList(requestData.getData()));
        return data;
    }
    @PostMapping("/addOnlinePay")
    public ResultData addOnlinePay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.addOnlinePay(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/updateOnlinePay")
    public ResultData updateOnlinePay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateOnlinePay(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/deleteOnlinePay")
    public ResultData deleteOnlinePay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.deleteOnlinePay(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }

    @GetMapping("/getConfigPayList")
    public ResultData getConfigPayList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getConfigPayList(requestData.getData()));
        return data;
    }
    @PostMapping("/addConfigPay")
    public ResultData addConfigPay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.addConfigPay(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/updateConfigPay")
    public ResultData updateConfigPay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateConfigPay(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/deleteConfigPay")
    public ResultData deleteConfigPay(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.deleteConfigPay(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }

    @GetMapping("/getShowPayOrderList")
    public ResultData getShowPayOrderList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getShowPayOrderList(requestData.getData()));
        return data;
    }
    @PostMapping("/ShowPayOrderSuccess")
    public ResultData ShowPayOrderSuccess(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.ShowPayOrderSuccess(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能重复设置状态");
        }
        return data;
    }
    @PostMapping("/ShowPayOrderFail")
    public ResultData ShowPayOrderFail(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.ShowPayOrderFail(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能由成功转为失败状态");
        }
        return data;
    }

    @GetMapping("/getPidList")
    public ResultData getPidList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getPidList(requestData.getData()));
        return data;
    }
    @GetMapping("/getOnlinePayOrderList")
    public ResultData getOnlinePayOrderList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getOnlinePayOrderList(requestData.getData()));
        return data;
    }
    @PostMapping("/OnlinePayOrderSuccess")
    public ResultData OnlinePayOrderSuccess(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.OnlinePayOrderSuccess(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能重复设置状态");
        }
        return data;
    }
    @PostMapping("/OnlinePayOrderFail")
    public ResultData OnlinePayOrderFail(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.OnlinePayOrderFail(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能由成功转为失败状态");
        }
        return data;
    }
}
