package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.PayService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/api/pay", tags = "支付中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/pay")
public class PayControl {
    @Autowired
    private PayService service;

    @ApiOperation(value = "获取所有支付配置", notes = "支付配置")
    @GetMapping("/getConfigPayList")
    public ResultData getConfigPayList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getConfigPayList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加配置", notes = "支付配置")
    @PostMapping("/addConfigPay")
    public ResultData addConfigPay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.addConfigPay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新配置", notes = "支付配置")
    @PostMapping("/updateConfigPay")
    public ResultData updateConfigPay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateConfigPay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除配置", notes = "支付配置")
    @PostMapping("/deleteConfigPay")
    public ResultData deleteConfigPay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.deleteConfigPay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取所有绑定ID", notes = "在线支付")
    @GetMapping("/getTypeList")
    public ResultData getTypeList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getTypeList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取所有支付配置", notes = "支付方式")
    @GetMapping("/getOnlinePayList")
    public ResultData getOnlinePayList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getOnlinePayList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加支付方式", notes = "支付方式")
    @PostMapping("/addOnlinePay")
    public ResultData addOnlinePay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.addOnlinePay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新支付方式", notes = "支付方式")
    @PostMapping("/updateOnlinePay")
    public ResultData updateOnlinePay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateOnlinePay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除支付方式", notes = "支付方式")
    @PostMapping("/deleteOnlinePay")
    public ResultData deleteOnlinePay(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.deleteOnlinePay(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }


    @ApiOperation(value = "获取所有第三方支付订单", notes = "第三方支付订单")
    @GetMapping("/getShowPayOrderList")
    public ResultData getShowPayOrderList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getShowPayOrderList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "改变第三方订单状态为成功", notes = "第三方支付订单")
    @PostMapping("/ShowPayOrderSuccess/{id}")
    public ResultData ShowPayOrderSuccess(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.ShowPayOrderSuccess(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能重复设置状态");
        }
        return data;
    }
    @ApiOperation(value = "改变第三方订单状态为失败", notes = "第三方支付订单")
    @PostMapping("/ShowPayOrderFail/{id}")
    public ResultData ShowPayOrderFail(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.ShowPayOrderFail(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能由成功转为失败状态");
        }
        return data;
    }
    @ApiOperation(value = "获取所有支付方式", notes = "支付订单")
    @GetMapping("/getPidList")
    public ResultData getPidList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getPidList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取所有平台订单", notes = "平台订单")
    @GetMapping("/getOnlinePayOrderList")
    public ResultData getOnlinePayOrderList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(service.getOnlinePayOrderList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "改变平台订单状态为成功", notes = "平台订单")
    @PostMapping("/OnlinePayOrderSuccess/{id}")
    public ResultData OnlinePayOrderSuccess(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.OnlinePayOrderSuccess(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能重复设置状态");
        }
        return data;
    }
    @ApiOperation(value = "改变平台订单状态为失败", notes = "平台订单")
    @PostMapping("/OnlinePayOrderFail/{id}")
    public ResultData OnlinePayOrderFail(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.OnlinePayOrderFail(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能由成功转为失败状态");
        }
        return data;
    }
    @ApiOperation(value = "获取所有游戏充值订单", notes = "游戏充值订单")
    @GetMapping("/getGameOrderList")
    public ResultData getGameOrderList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getGameOrderList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "改变游戏充值订单状态为成功", notes = "游戏充值订单")
    @PostMapping("/GameOrderSuccess/{id}")
    public ResultData GameOrderSuccess(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.GameOrderSuccess(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能重复设置状态");
        }
        return data;
    }
    @ApiOperation(value = "改变游戏充值订单状态为失败", notes = "游戏充值订单")
    @PostMapping("/GameOrderFail")
    public ResultData GameOrderFail(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!service.GameOrderFail(id)) {
            data.setCode(201);
            data.setMessage("修改失败！所有订单不能由成功转为失败状态");
        }
        return data;
    }
}
