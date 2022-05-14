package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.WithdrawService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(value = "/api/withdraw", tags = "提现中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/withdraw")
public class WithdrawControl {
    @Autowired
    private WithdrawService service;

    @ApiOperation(value = "获取提现列表", notes = "提现")
    @GetMapping("/getList")
    public ResultData getList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "审核订单", notes = "提现")
    @PostMapping("/handleShenHe/{id}")
    public ResultData handleShenHe(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.handleShenHe(id, user)){
            data.setCode(201);
            data.setMessage("审核失败！");
        }
        return data;
    }
    @ApiOperation(value = "更新订单", notes = "提现")
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.update(requestData.getData(), user)){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @ApiOperation(value = "退回订单", notes = "提现")
    @PostMapping("/handleBack")
    public ResultData handleBack(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.handleBack(requestData.getData())){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @ApiOperation(value = "删除订单", notes = "提现")
    @PostMapping("/del/{id}")
    public ResultData del(@PathVariable("id") long id, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.del(id,systemUser)){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取提现卡号列表", notes = "提现卡号")
    @GetMapping("/getCardList")
    public ResultData getCardList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(service.getCardList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新提现卡号", notes = "提现卡号")
    @PostMapping("/updateCard")
    public ResultData updateCard(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.updateCard(requestData.getData(), systemUser)){
            data.setCode(201);
            data.setMessage("操作失败！");
        }
        return data;
    }
    @ApiOperation(value = "删除提现卡号", notes = "提现卡号")
    @PostMapping("/delCard/{id}")
    public ResultData delCard(@PathVariable("id") long id,  @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!service.delCard(id,systemUser)){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
}
