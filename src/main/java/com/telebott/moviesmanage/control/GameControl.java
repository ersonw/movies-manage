package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(value = "/api/Game", tags = "游戏中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/Game")
public class GameControl {
    @Autowired
    private GameService gameService;

    @ApiOperation(value = "获取游戏接口配置", notes = "")
    @GetMapping("/configList")
    public ResultData configList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(gameService.configList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新游戏接口配置", notes = "")
    @PostMapping("/updateConfig")
    public ResultData updateConfig(@RequestBody RequestData requestData){
        ResultData data = new ResultData();
        data.setCode(201);
        if (gameService.updateConfig(requestData.getData())){
            data.setCode(200);
        }
        return data;
    }
    @ApiOperation(value = "获取游戏配置", notes = "")
    @GetMapping("/getList")
    public ResultData getList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(gameService.getList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "更新游戏配置", notes = "")
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData){
        ResultData data = new ResultData();
        data.setCode(201);
        if (gameService.update(requestData.getData())){
            data.setCode(200);
        }
        return data;
    }
    @ApiOperation(value = "删除游戏配置", notes = "")
    @PostMapping("/delete/{id}")
    public ResultData delete(@PathVariable("id") long id){
        ResultData data = new ResultData();
        data.setCode(201);
        if (gameService.delete(id)){
            data.setCode(200);
        }
        return data;
    }

    @ApiOperation(value = "获取游戏历史", notes = "")
    @GetMapping("/getRecordList")
    public ResultData getRecordList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(gameService.getRecordList(requestData.getData()));
        return data;
    }
}
