package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.APPConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/APPConfig")
public class APPConfigControl {
    @Autowired
    private APPConfigService appConfigService;
    @GetMapping("/updateConfig")
    public ResultData updateConfig(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(appConfigService.updateConfig());
        return data;
    }
    @GetMapping("/getAPPConfigList")
    public ResultData getAPPConfigList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(appConfigService.getAPPConfigList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateAPPConfigList")
    public ResultData updateAPPConfigList(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.updateAPPConfigList(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @GetMapping("/getAPPConfigVersionList")
    public ResultData getAPPConfigVersionList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(appConfigService.getAPPConfigVersionList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateAPPConfigVersionList")
    public ResultData updateAPPConfigVersionList(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.updateAPPConfigVersionList(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @PostMapping("/addAPPConfigVersionList")
    public ResultData addAPPConfigVersionList(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.addAPPConfigVersionList(requestData.getData())){
            data.setCode(201);
            data.setMessage("发布失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @PostMapping("/deleteAPPConfigVersionList")
    public ResultData deleteAPPConfigVersionList(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.deleteAPPConfigVersionList(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @GetMapping("/getCarouselList")
    public ResultData getCarouselList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(appConfigService.getCarouselList(requestData.getData()));
        return data;
    }
    @PostMapping("/updateCarousel")
    public ResultData updateCarousel(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.updateCarousel(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @PostMapping("/addCarousel")
    public ResultData addCarousel(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.addCarousel(requestData.getData())){
            data.setCode(201);
            data.setMessage("发布失败！缺少必填参数配置不存在");
        }
        return data;
    }
    @PostMapping("/deleteCarousel")
    public ResultData deleteCarousel(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!appConfigService.deleteCarousel(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！缺少必填参数配置不存在");
        }
        return data;
    }

}
