package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Commodity")
public class CommodityControl {
    @Autowired
    private CommodityService commodityService;
    @GetMapping("/getCommodityVipList")
    public ResultData getCommodityVipList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityVipList(requestData.getData()));
        return data;
    }
    @PostMapping("/addCommodityVip")
    public ResultData addCommodityVip(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityVip(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/updateCommodityVip")
    public ResultData updateCommodityVip(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityVip(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/deleteCommodityVip")
    public ResultData deleteCommodityVip(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityVip(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @GetMapping("/getCommodityDiamondList")
    public ResultData getCommodityDiamondList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityDiamondList(requestData.getData()));
        return data;
    }
    @PostMapping("/addCommodityDiamond")
    public ResultData addCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/updateCommodityDiamond")
    public ResultData updateCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @PostMapping("/deleteCommodityDiamond")
    public ResultData deleteCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityDiamond(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
}
