package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.CommodityService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/api/Commodity", tags = "商品中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/Commodity")
public class CommodityControl {
    @Autowired
    private CommodityService commodityService;
    @ApiOperation(value = "获取会员商品列表", notes = "会员商品")
    @GetMapping("/getCommodityVipList")
    public ResultData getCommodityVipList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityVipList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加会员商品", notes = "会员商品")
    @PostMapping("/addCommodityVip")
    public ResultData addCommodityVip(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityVip(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新会员商品", notes = "会员商品")
    @PostMapping("/updateCommodityVip")
    public ResultData updateCommodityVip(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityVip(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除会员商品", notes = "会员商品")
    @PostMapping("/deleteCommodityVip")
    public ResultData deleteCommodityVip(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityVip(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取钻石商品列表", notes = "钻石商品")
    @GetMapping("/getCommodityDiamondList")
    public ResultData getCommodityDiamondList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityDiamondList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加钻石商品", notes = "钻石商品")
    @PostMapping("/addCommodityDiamond")
    public ResultData addCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityDiamond(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新钻石商品", notes = "钻石商品")
    @PostMapping("/updateCommodityDiamond")
    public ResultData updateCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityDiamond(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除钻石商品", notes = "钻石商品")
    @PostMapping("/deleteCommodityDiamond")
    public ResultData deleteCommodityDiamond(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityDiamond(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取金币商品列表", notes = "金币商品")
    @GetMapping("/getCommodityGoldList")
    public ResultData getCommodityGoldList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityGoldList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加金币商品", notes = "金币商品")
    @PostMapping("/addCommodityGold")
    public ResultData addCommodityGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityGold(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新金币商品", notes = "金币商品")
    @PostMapping("/updateCommodityGold")
    public ResultData updateCommodityGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityGold(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且标题不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除金币商品", notes = "金币商品")
    @PostMapping("/deleteCommodityGold")
    public ResultData deleteCommodityGold(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityGold(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取游戏商品列表", notes = "游戏商品")
    @GetMapping("/getCommodityGameList")
    public ResultData getCommodityGameList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(commodityService.getCommodityGameList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加游戏商品", notes = "游戏商品")
    @PostMapping("/addCommodityGame")
    public ResultData addCommodityGame(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.addCommodityGame(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！所有项必填且金额不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新游戏商品", notes = "游戏商品")
    @PostMapping("/updateCommodityGame")
    public ResultData updateCommodityGame(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.updateCommodityGame(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！所有项必填且金额不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除游戏商品", notes = "游戏商品")
    @PostMapping("/deleteCommodityGame")
    public ResultData deleteCommodityGame(@RequestBody  RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!commodityService.deleteCommodityGame(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
}
