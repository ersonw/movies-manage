package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Game")
public class GameControl {
    @Autowired
    private GameService gameService;;
    @GetMapping("/configList")
    public ResultData configList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(gameService.configList(requestData.getData()));
        return data;
    }
    @GetMapping("/getList")
    public ResultData getList(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(gameService.getList(requestData.getData()));
        return data;
    }
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData){
        ResultData data = new ResultData();
        data.setCode(201);
        if (gameService.update(requestData.getData())){
            data.setCode(200);
        }
        return data;
    }

}
