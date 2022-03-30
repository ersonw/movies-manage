package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
public class VideosControl {
    @Autowired
    private VideosService videosService;
    @GetMapping("/getUnActorVideos")
    public ResultData getUnActorVideos(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getUnActorVideos(requestData.getData()));
        return data;
    }
    @GetMapping("/getActorList")
    public ResultData getActorList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getActorList(requestData.getData()));
        return data;
    }
    @GetMapping("/getMeasurements")
    public ResultData getMeasurements(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getMeasurements(requestData.getData()));
        return data;
    }
    @PostMapping("/addActor")
    public ResultData addActor(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addActor(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！姓名与头像必须填写且不能重复");
        }
        return data;
    }
    @PostMapping("/deleteActor")
    public ResultData deleteActor(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteActor(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！演员不存在");
        }
        return data;
    }
    @PostMapping("/removeActorVideo")
    public ResultData removeActorVideo(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.removeActorVideo(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！演员不存在");
        }
        return data;
    }
    @PostMapping("/updateActor")
    public ResultData updateActor(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateActor(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！演员不存在");
        }
        return data;
    }
    @GetMapping("/getBoutiqueList")
    public ResultData getBoutiqueList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getBoutiqueList(requestData.getData()));
        return data;
    }
    @PostMapping("/addBoutique")
    public ResultData addBoutique(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addBoutique(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！类名必须填写且不能重复");
        }
        return data;
    }
    @PostMapping("/deleteBoutique")
    public ResultData deleteBoutique(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteBoutique(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @PostMapping("/deleteRecord")
    public ResultData deleteRecord(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteRecord(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @PostMapping("/updateBoutique")
    public ResultData updateBoutique(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateBoutique(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！分类不存在");
        }
        return data;
    }
    @GetMapping("/getClassList")
    public ResultData getClassList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getClassList(requestData.getData()));
        return data;
    }
    @PostMapping("/addClass")
    public ResultData addClass(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addClass(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败！类名必须填写且不能重复");
        }
        return data;
    }
    @PostMapping("/updateClass")
    public ResultData updateClass(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateClass(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @PostMapping("/deleteClass")
    public ResultData deleteClass(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteClass(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！分类不存在");
        }
        return data;
    }
    @GetMapping("/list")
    public ResultData list(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getList(requestData.getData()));
        return data;
    }
    @GetMapping("/getActorVideos")
    public ResultData getActorVideos(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getActorVideos(requestData.getData()));
        return data;
    }
    @GetMapping("/getClass")
    public ResultData getClass(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getVodClass());
        return data;
    }
    @GetMapping("/getActor")
    public ResultData getActor(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getActor());
        return data;
    }
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.update(requestData.getData())){
            data.setCode(201);
            data.setMessage("修改失败！视频不存在");
        }
        return data;
    }
    @PostMapping("/delete")
    public ResultData delete(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.delete(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！视频不存在");
        }
        return data;
    }
}
