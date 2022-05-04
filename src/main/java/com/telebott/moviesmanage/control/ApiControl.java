package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.*;
import com.telebott.moviesmanage.service.*;
import com.telebott.moviesmanage.util.TimeUtil;
import com.telebott.moviesmanage.util.WaLiUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
@Api(value = "api", tags = "无验证控制器")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api")
public class ApiControl {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private OnlineOrderService onlineOrderService;
    @Autowired
    private VideosService videosService;

    @ApiOperation(value="请求的接口示例", notes = "测试接口")
//    @ApiImplicitParam(name = "s",value = "ss",readOnly = true,dataType = "String", paramType = "path")
    @GetMapping("/test")
    public ResultData test(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        System.out.println(TimeUtil.manyDaysLater(10));
        return data;
    }

    private String getJsonBodyString(HttpServletRequest httpServletRequest) {
        try {
            httpServletRequest.setCharacterEncoding("UTF-8");
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), StandardCharsets.UTF_8));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @ApiIgnore
    @PostMapping("/Yzm")
    public ResultData Yzm(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getMethod());
        ResultData data = new ResultData();
        String jsonStr = getJsonBodyString(httpServletRequest);
        if (jsonStr != null) {
            JSONObject object = JSONObject.parseObject(jsonStr);
            if (object != null) {
                YzmData yzmData = JSONObject.toJavaObject(object, YzmData.class);
                if (yzmData != null && yzmData.getResult().equals("ok")) {
                    videosService.handlerYzm(yzmData);
                }
            }
        }
        return data;
    }
    @ApiIgnore
    @PostMapping("/toPayNotify")
    public String toPayNotify(@ModelAttribute ToPayNotify payNotify) {
        System.out.println(payNotify);
        if (onlineOrderService.handlerToPayNotify(payNotify)) {
            return "success";
        }
        return "fail";
    }
    @ApiIgnore
    @GetMapping("/toPay")
    public String toPay(@ModelAttribute ToPayNotify payNotify) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<script type=\"text/javascript\">\n" +
                "\n" +
                "    function run(){\n" +
                "        document.getElementById(\"sp\").click();\n" +
                "    }\n" +
                "</script>\n" +
                "<body οnlοad=\"run()\">\n" +
                "<a href=\"moviescheme://123\">打开应用<h1 id=\"sp\"></h1></a>\n" +
                "</body>\n" +
                "</html>";
    }

    @ApiOperation(value="登录请求", notes = "登录请求获取Token")
    @PostMapping("/login")
    public ResultData login(@RequestBody LoginData loginData) {
        ResultData data = new ResultData();
        SystemUser systemUser = loginData.getUser();
        if (systemUser == null) {
            systemUser = systemUserService.login(loginData);
        }
        if (systemUser == null) {
            data.setCode(201);
            data.setMessage("用户不存在或者密码错误！");
        } else {
            if (systemUser.getStatus() == 1) {
                data.setData(systemUserService.getLoginResult(systemUser));
            } else {
                data.setCode(201);
                data.setMessage("用户已被禁用，请联系管理员!");
            }
        }
        return data;
    }
}
