package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value="钻石充值", notes = "获取钻石充值记录")
    @GetMapping("/diamond")
    public ResultData diamond(){
        ResultData data = new ResultData();
        return data;
    }
}
