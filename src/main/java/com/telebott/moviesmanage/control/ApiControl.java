package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiControl {
    @Autowired
    private SystemConfigService systemConfigService;
    @GetMapping("/test")
    public ResultData test(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        return data;
    }
}
