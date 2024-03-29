package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.RechargeCentreService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(value = "/api/RechargeCentre", tags = "余额中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/RechargeCentre")
public class RechargeCentreControl {
    @Autowired
    private RechargeCentreService rechargeCentreService;

    @ApiOperation(value = "根据ID检查用户是否存在", notes = "")
    @GetMapping("/checkUser")
    public ResultData checkUser(@ModelAttribute RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.checkUser(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取余额订单列表", notes = "")
    @GetMapping("/getBalanceList")
    public ResultData getBalanceList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getBalanceList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新余额订单", notes = "")
    @PostMapping("/updateBalance")
    public ResultData updateBalance(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除余额订单", notes = "")
    @PostMapping("/deleteBalance")
    public ResultData deleteBalance(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @ApiOperation(value = "添加余额订单", notes = "")
    @PostMapping("/addBalance")
    public ResultData addBalance(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取钻石订单列表", notes = "")
    @GetMapping("/getDiamondList")
    public ResultData getDiamondList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getDiamondList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新钻石订单", notes = "")
    @PostMapping("/updateDiamond")
    public ResultData updateDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @ApiOperation(value = "添加钻石订单", notes = "")
    @PostMapping("/addDiamond")
    public ResultData addDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除钻石订单", notes = "")
    @PostMapping("/deleteDiamond")
    public ResultData deleteDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取金币订单列表", notes = "")
    @GetMapping("/getGoldList")
    public ResultData getGoldList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getGoldList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新金币订单", notes = "")
    @PostMapping("/updateGold")
    public ResultData updateGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @ApiOperation(value = "添加金币订单", notes = "")
    @PostMapping("/addGold")
    public ResultData addGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除金币订单", notes = "")
    @PostMapping("/deleteGold")
    public ResultData deleteGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }

}
