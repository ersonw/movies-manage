package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.RechargeCentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/RechargeCentre")
public class RechargeCentreControl {
    @Autowired
    private RechargeCentreService rechargeCentreService;
    @GetMapping("/checkUser")
    public ResultData checkUser(@ModelAttribute RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.checkUser(requestData.getData()));
        return data;
    }
    @GetMapping("/getBalanceList")
    public ResultData getBalanceList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getBalanceList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateBalance")
    public ResultData updateBalance(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @PostMapping("/deleteBalance")
    public ResultData deleteBalance(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @PostMapping("/addBalance")
    public ResultData addBalance(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addBalance(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }

    @GetMapping("/getDiamondList")
    public ResultData getDiamondList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getDiamondList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateDiamond")
    public ResultData updateDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @PostMapping("/addDiamond")
    public ResultData addDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @PostMapping("/deleteDiamond")
    public ResultData deleteDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }

    @GetMapping("/getGoldList")
    public ResultData getGoldList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(rechargeCentreService.getGoldList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateGold")
    public ResultData updateGold(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.updateGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！订单不存在");
        }
        return data;
    }
    @PostMapping("/addGold")
    public ResultData addGold(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.addGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！订单号不存在");
        }
        return data;
    }
    @PostMapping("/deleteGold")
    public ResultData deleteGold(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!rechargeCentreService.deleteGold(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！缺少必填参数或者用户不存在");
        }
        return data;
    }

}
