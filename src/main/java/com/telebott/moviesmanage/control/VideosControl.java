package com.telebott.moviesmanage.control;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.entity.SystemUser;
import com.telebott.moviesmanage.service.VideosService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "/api/videos", tags = "视频中心")
@ApiResponses({
        @ApiResponse(code = 200, message = "成功"),
        @ApiResponse(code = 105, message = "未带token请求"),
        @ApiResponse(code = 106, message = "token非法或者登录已过期"),
})
@RestController
@RequestMapping("/api/videos")
public class VideosControl {
    @Autowired
    private VideosService videosService;

    @ApiOperation(value = "获取视频列表", notes = "获取视频列表")
    @GetMapping("/list")
    public ResultData list(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getList(requestData.getData()));
        return data;
    }

    @ApiOperation(
            value = "更新视频", httpMethod = "POST",
            produces = "application/json", response = ResultData.class,
            notes = "删除视频" +
                    "提交data:{list: [{id:1,title:'12313'},{id:2,title:'12313'}]}" +
                    "<p>成功：code = 200 | 失败：code = 201 并返回错误码，如下：</p>"
    )
    @PostMapping("/update")
    public ResultData update(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.update(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！视频不存在");
        }
        return data;
    }

    @ApiOperation(
            value = "删除视频", httpMethod = "POST",
            produces = "application/json", response = ResultData.class,
            notes = "删除视频" +
                    "提交data:{list: [{id:1},{id:2}]}" +
                    "<p>成功：code = 200 | 失败：code = 201 并返回错误码，如下：</p>" +
                    "<p>错误码：</p>"
    )
    @PostMapping("/delete")
    public ResultData delete(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.delete(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！视频不存在");
        }
        return data;
    }

    @ApiOperation(value = "清空所有", notes = "清空所有")
    @PostMapping("/clean")
    public ResultData clean(@RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.clean()) {
            data.setCode(201);
            data.setMessage("清除失败！视频不存在");
        }
        return data;
    }


    @ApiOperation(value = "获取所有分类", notes = "获取所有分类")
    @GetMapping("/getClass")
    public ResultData getClass(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getVodClass());
        return data;
    }
    @ApiOperation(value = "搜索分类", notes = "获取所有分类")
    @GetMapping("/getClassList")
    public ResultData getClassList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getClassList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加分类", notes = "添加分类")
    @PostMapping("/addClass")
    public ResultData addClass(@RequestBody RequestData requestData, @RequestAttribute  @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addClass(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！类名必须填写且不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新分类", notes = "更新分类")
    @PostMapping("/updateClass")
    public ResultData updateClass(@RequestBody RequestData requestData, @RequestAttribute String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateClass(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除分类", notes = "删除分类")
    @PostMapping("/deleteClass")
    public ResultData deleteClass(@RequestBody RequestData requestData, @RequestAttribute  @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteClass(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！分类不存在");
        }
        return data;
    }
    @ApiOperation(value = "清空所有分类", notes = "清空所有")
    @PostMapping("/cleanClass")
    public ResultData cleanClass(@RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.cleanClass()) {
            data.setCode(201);
            data.setMessage("清除失败！视频不存在");
        }
        return data;
    }

    @ApiOperation(value = "获取精品分类列表", notes = "获取精品列表")
    @GetMapping("/getBoutiqueList")
    public ResultData getBoutiqueList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getBoutiqueList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "新增精品分类", notes = "新增精品")
    @PostMapping("/addBoutique")
    public ResultData addBoutique(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addBoutique(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！类名必须填写且不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除精品分类", notes = "删除精品")
    @PostMapping("/deleteBoutique")
    public ResultData deleteBoutique(@RequestBody RequestData requestData, @RequestAttribute String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteBoutique(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @ApiOperation(value = "更新精品分类", notes = "更新精品")
    @PostMapping("/updateBoutique")
    public ResultData updateBoutique(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateBoutique(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！分类不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除精品视频", notes = "删除精品视频")
    @PostMapping("/deleteRecord")
    public ResultData deleteRecord(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteRecord(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！分类不存在");
        }
        return data;
    }
    @ApiOperation(value = "清空所有精品", notes = "清空所有")
    @PostMapping("/cleanBoutique")
    public ResultData cleanBoutique(@RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.cleanBoutique()) {
            data.setCode(201);
            data.setMessage("清除失败！视频不存在");
        }
        return data;
    }

    @ApiOperation(value = "获取未归属女优视频", notes = "获取未归属女优视频")
    @GetMapping("/getUnActorVideos")
    public ResultData getUnActorVideos(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getUnActorVideos(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "搜索女优列表", notes = "获取女优列表")
    @GetMapping("/getActorList")
    public ResultData getActorList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getActorList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取三围列表", notes = "获取三围列表")
    @GetMapping("/getMeasurements")
    public ResultData getMeasurements(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getMeasurements(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加女优", notes = "女优")
    @PostMapping("/addActor")
    public ResultData addActor(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addActor(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！姓名与头像必须填写且不能重复");
        }
        return data;
    }
    @ApiOperation(value = "删除女优", notes = "女优")
    @PostMapping("/deleteActor")
    public ResultData deleteActor(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteActor(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！演员不存在");
        }
        return data;
    }
    @ApiOperation(value = "移除归属女优", notes = "女优")
    @PostMapping("/removeActorVideo")
    public ResultData removeActorVideo(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.removeActorVideo(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！演员不存在");
        }
        return data;
    }
    @ApiOperation(value = "更新女优信息", notes = "女优")
    @PostMapping("/updateActor")
    public ResultData updateActor(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateActor(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！演员不存在");
        }
        return data;
    }
    @ApiOperation(value = "获取归属女优视频", notes = "女优")
    @GetMapping("/getActorVideos/{id}/{page}")
    public ResultData getActorVideos(@PathVariable("id") long id,@PathVariable(value = "page", required = false) int page) {
        ResultData data = new ResultData();
        data.setData(videosService.getActorVideos(page, id));
        return data;
    }
    @ApiOperation(value = "获取所有女优", notes = "女优")
    @GetMapping("/getActor")
    public ResultData getActor(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getActor());
        return data;
    }


    @ApiOperation(value = "获取购买订单", notes = "订单")
    @GetMapping("/getVideoOrdersList")
    public ResultData getVideoOrdersList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getVideoOrdersList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "根据用户ID删除购买订单", notes = "订单")
    @PostMapping("/removeOrderUser")
    public ResultData removeOrderUser(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.removeOrderUser(requestData.getData())) {
            data.setCode(201);
            data.setMessage("移除失败！订单不存在");
        }
        return data;
    }
    @ApiOperation(value = "根据视频ID删除购买订单", notes = "订单")
    @PostMapping("/deleteOrderVideo")
    public ResultData deleteOrderVideo(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteOrderVideo(requestData.getData())) {
            data.setCode(201);
            data.setMessage("移除失败！订单不存在");
        }
        return data;
    }



    @ApiOperation(value = "获取狼友推荐列表", notes = "狼友推荐")
    @GetMapping("/getWolfFriendList")
    public ResultData getWolfFriendList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getWolfFriendList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取狼友点赞列表", notes = "狼友推荐")
    @GetMapping("/getRecommendVideoList")
    public ResultData getRecommendVideoList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getRecommendVideoList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "删除单个推荐理由", notes = "狼友推荐")
    @PostMapping("/removeWolfUser")
    public ResultData removeWolfUser(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.removeWolfUser(requestData.getData())) {
            data.setCode(201);
            data.setMessage("移除失败！订单不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除该视频所有推荐理由", notes = "狼友推荐")
    @PostMapping("/deleteWolf")
    public ResultData deleteWolf(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteWolf(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！订单不存在");
        }
        return data;
    }


    @ApiOperation(value = "获取编辑推荐列表", notes = "编辑推荐")
    @GetMapping("/getEditorRecommendList")
    public ResultData getEditorRecommendList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getEditorRecommendList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "获取编辑推荐视频列表", notes = "编辑推荐")
    @GetMapping("/getEditorRecommendVideoList")
    public ResultData getEditorRecommendVideoList(@ModelAttribute RequestData requestData) {
        ResultData data = new ResultData();
        data.setData(videosService.getEditorRecommendVideoList(requestData.getData()));
        return data;
    }
    @ApiOperation(value = "添加编辑推荐视频", notes = "编辑推荐")
    @PostMapping("/addEditorRecommend")
    public ResultData addEditorRecommend(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.addEditorRecommend(requestData.getData())) {
            data.setCode(201);
            data.setMessage("添加失败！电影必须选择且展示时间必须填写且当天影视不能重复");
        }
        return data;
    }
    @ApiOperation(value = "更新编辑推荐视频", notes = "编辑推荐")
    @PostMapping("/updateEditorRecommend")
    public ResultData updateEditorRecommend(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.updateEditorRecommend(requestData.getData())) {
            data.setCode(201);
            data.setMessage("修改失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除指定日期编辑推荐视频", notes = "编辑推荐")
    @PostMapping("/deleteEditorRecommend")
    public ResultData deleteEditorRecommend(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.deleteEditorRecommend(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
    @ApiOperation(value = "删除单个编辑推荐视频", notes = "编辑推荐")
    @PostMapping("/removeEditorRecommend")
    public ResultData removeEditorRecommend(@RequestBody RequestData requestData, @RequestAttribute @ApiParam(hidden = true) String user) {
        SystemUser systemUser = JSONObject.toJavaObject(JSONObject.parseObject(user), SystemUser.class);
        ResultData data = new ResultData();
        if (!videosService.removeEditorRecommend(requestData.getData())) {
            data.setCode(201);
            data.setMessage("删除失败！ID不存在");
        }
        return data;
    }
}
