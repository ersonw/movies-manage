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
    @GetMapping("/list")
    public ResultData list(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(videosService.getList(requestData.getData()));
        return data;
    }
    @PostMapping("/delete")
    public ResultData delete(@RequestBody RequestData requestData, @RequestAttribute String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.delete(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！用户不存在");
        }
        return data;
    }
}