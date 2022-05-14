package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/api/users", tags = "用户中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/users")
public class UsersControl {
    @Autowired
    private UserService userService;

    @ApiOperation(value="获取会员列表", notes = "获取会员列表",produces = "application/json")
    @GetMapping("/vipList")
    public ResultData vipList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.vipList(requestData.getData()));
        return data;
    }
    @ApiOperation(value="添加会员", notes = "添加会员")
    @PostMapping("/vipAdd")
    public ResultData vipAdd(@RequestBody RequestData requestData){
        ResultData data = new ResultData();
        if (!userService.vipAdd(requestData.getData())){
            data.setCode(201);
            data.setMessage("添加失败");
        }
        return data;
    }
    @ApiOperation(value="清空所有会员", notes = "清空所有会员")
    @PostMapping("/cleanVip")
    public ResultData cleanVip(@RequestAttribute @ApiParam(hidden = true)  String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.cleanVip()){
            data.setCode(201);
            data.setMessage("清除失败！用户不存在");
        }
        return data;
    }
    @ApiOperation(value="获取用户列表", notes = "获取用户列表",produces = "application/json")
    @GetMapping("/list")
    public ResultData list(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.getList(requestData.getData()));
        return data;
    }

    @ApiOperation(value="删除单个用户", notes = "删除单个用户")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true)  String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.delete(requestData.getData())){
            data.setCode(201);
            data.setMessage("删除失败！用户不存在");
        }
        return data;
    }
    @ApiOperation(value="清空所有用户", notes = "清空所有用户")
    @PostMapping("/clean")
    public ResultData clean(@RequestAttribute @ApiParam(hidden = true)  String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.clean()){
            data.setCode(201);
            data.setMessage("清除失败！用户不存在");
        }
        return data;
    }
    @ApiOperation(value="更新单个用户", notes = "删除单个用户")
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true)  String user){
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        if (!userService.update(requestData.getData())){
            data.setCode(201);
            data.setMessage("更新失败！用户不存在");
        }
        return data;
    }
    @ApiOperation(value="查询用户上级", notes = "查询用户上级")
    @GetMapping("/superior/{uid}")
    public ResultData superior(@PathVariable("uid") long uid){
        ResultData data = new ResultData();
        data.setData(userService.superior(uid));
        return data;
    }
    @ApiOperation(value="查询用户下级", notes = "查询用户下级")
    @GetMapping("/share/{uid}")
    public ResultData share(@PathVariable("uid") long uid){
        ResultData data = new ResultData();
        data.setData(userService.share(uid));
        return data;
    }
    @ApiOperation(value="查询短信记录", notes = "查询短信记录")
    @GetMapping("/getSmsRecordsList")
    public ResultData getSmsRecordsList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(userService.getSmsRecordsList(requestData.getData()));
        return data;
    }
}
