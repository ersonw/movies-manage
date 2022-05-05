package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/api/Dashboard", tags = "仪表盘")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/Dashboard")
public class DashboardControl {
    @Autowired
    private DashboardService dashboardService;
    @ApiOperation(value="钻石充值", notes = "获取钻石充值统计")
    @GetMapping("/diamond")
    public ResultData diamond(){
        ResultData data = new ResultData();
        data.setData(dashboardService.diamond());
        return data;
    }
    @ApiOperation(value="会员充值", notes = "获取会员充值统计")
    @GetMapping("/vip")
    public ResultData vip(){
        ResultData data = new ResultData();
        data.setData(dashboardService.vip());
        return data;
    }
    @ApiOperation(value="游戏充值", notes = "获取游戏充值统计")
    @GetMapping("/game")
    public ResultData game(){
        ResultData data = new ResultData();
        data.setData(dashboardService.game());
        return data;
    }
    @ApiOperation(value="提现", notes = "获取提现统计")
    @GetMapping("/withdrawal")
    public ResultData withdrawal(){
        ResultData data = new ResultData();
        data.setData(dashboardService.withdrawal());
        return data;
    }
    @ApiOperation(value="扣量", notes = "获取扣量统计")
    @GetMapping("/deduction")
    public ResultData deduction(){
        ResultData data = new ResultData();
        data.setData(dashboardService.deduction());
        return data;
    }
    @ApiOperation(value="用户", notes = "获取用户统计")
    @GetMapping("/users")
    public ResultData users(){
        ResultData data = new ResultData();
        data.setData(dashboardService.users());
        return data;
    }
    @ApiOperation(value="其他", notes = "获取其他统计")
    @GetMapping("/other")
    public ResultData other(){
        ResultData data = new ResultData();
        data.setData(dashboardService.other());
        return data;
    }
}
