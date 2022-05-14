package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.AuthDao;
import com.telebott.moviesmanage.entity.LoginData;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.SystemUserService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/api/user", tags = "管理员")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/user")
public class UserControl {
    @Autowired
    private SystemUserService systemUserService;


    @ApiOperation(value="获取管理员信息", notes = "通过TOKEN获取管理员信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "pageSize", value = "每页取几条记录", required = true, dataType = "Integer", paramType = "path")
//    })
    @GetMapping("/info")
    public ResultData info(@Param("user") @ApiParam(hidden = true) String user){
        ResultData data = new ResultData();
        if (StringUtils.isNotEmpty(user)){
            data.setData(systemUserService.getInfoResult(JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class)));
        }
        return data;
    }
    @ApiOperation(value="退出管理员登录", notes = "通过TOKEN退出管理员登录")
    @PostMapping("/logout")
    public ResultData logout(@RequestAttribute @ApiParam(hidden = true) String user){
//        System.out.println(user);
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user),SystemUser.class);
        ResultData data = new ResultData();
        systemUserService.logout(systemUser);
        return data;
    }
}
