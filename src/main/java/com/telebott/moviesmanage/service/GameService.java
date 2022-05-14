package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class GameService {
    @Autowired
    private WaLiGamesDao waLiGamesDao;
    @Autowired
    private WaLiConfigDao waLiConfigDao;
    @Autowired
    private WaliGameRecordsDao waliGameRecordsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private GameRecordsDao gameRecordsDao;
    @Autowired
    private WaLiConfigService waLiConfigService;

    public  static boolean isNumberString(String s){
        for (int i=0;i< s.length(); i++){
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }
    public JSONObject getList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 0;
        int limit = 20;
        if (data != null){
            if (data.get("page") != null && isNumberString(data.getString("page"))) page = data.getIntValue("page");
            if (data.get("limit") != null && isNumberString(data.getString("limit"))) limit = data.getIntValue("limit");
            page--;
            if (page < 0) page =0;
            if (limit < 0) limit =20;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<WaLiGames> waLiGamesPage;
        if (data != null && data.get("title") != null && StringUtils.isNotEmpty(data.getString("title"))){
            waLiGamesPage = waLiGamesDao.findAllByNameLike(data.getString("title"), pageable);
        }else {
            waLiGamesPage = waLiGamesDao.findAll(pageable);
        }
        object.put("list",waLiGamesPage.getContent());
        object.put("total",waLiGamesPage.getTotalElements());
        return object;
    }
    boolean _checkData(WaLiGames waLiGames){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(waLiGames));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        waLiGames = waLiGamesDao.findAllByGameId(waLiGames.getGameId());
        return waLiGames == null;
    }
    private WaLiGames  _changeData(JSONObject data){
//        System.out.println(data);
        WaLiGames waLiGames = waLiGamesDao.findAllById(data.getLong("id"));
        if (waLiGames != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(waLiGames));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            waLiGames = waLiGamesDao.findAllByGameId(data.getInteger("gameId"));
            if (waLiGames == null || waLiGames.getId() == object.getLong("id")){
                return JSONObject.toJavaObject(object,WaLiGames.class);
            }
        }
        return null;
    }
    public boolean update(JSONObject data) {
        if (data != null){
            data.put("updateTime",System.currentTimeMillis());
            if (data.get("addTime") == null || data.getLong("addTime") == 0){
                data.put("addTime",System.currentTimeMillis());
            }
            WaLiGames waLiGames = _changeData(data);
            if (waLiGames != null){
                waLiGamesDao.saveAndFlush(waLiGames);
                return true;
            }
        }
        return false;
    }
    public boolean delete(long id) {
        if (id > 0){
            WaLiGames games = waLiGamesDao.findAllById(id);
            if (games != null){
                waLiGamesDao.delete(games);
                return true;
            }
        }
        return false;
    }
    public JSONObject configList(JSONObject data) {
        JSONObject object = new JSONObject();
        object.put("apiUrl",waLiConfigService.getValueByName("apiUrl"));
        object.put("agentId",waLiConfigService.getValueByName("agentId"));
        object.put("apiUser",waLiConfigService.getValueByName("apiUser"));
        object.put("encryptKey",waLiConfigService.getValueByName("encryptKey"));
        object.put("signKey",waLiConfigService.getValueByName("signKey"));
        return object;
    }

    public boolean updateConfig(JSONObject data) {
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (entry.getValue() != null){
                WaLiConfig config = waLiConfigDao.findAllByName(entry.getValue().toString());
                if (config == null) {
                    config = new WaLiConfig();
                    config.setAddTime(System.currentTimeMillis());
                    config.setName(entry.getKey());
                }
                config.setUpdateTime(System.currentTimeMillis());
                config.setVal(entry.getValue().toString());
                waLiConfigDao.saveAndFlush(config);
            }
        }
        return true;
    }


    public JSONObject getRecordList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 0;
        int limit = 20;
        if (data != null){
            if (data.get("page") != null && isNumberString(data.getString("page"))) page = data.getIntValue("page");
            if (data.get("limit") != null && isNumberString(data.getString("limit"))) limit = data.getIntValue("limit");
            page--;
            if (page < 0) page =0;
            if (limit < 0) limit =20;
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<GameRecords> gameRecordsPage;
        if (data != null &&  data.get("title") != null && StringUtils.isNotEmpty(data.getString("title")) && isNumberString(data.getString("title"))){
            gameRecordsPage = gameRecordsDao.findAllByUid(data.getLong("title"), pageable);
        }else {
            gameRecordsPage = gameRecordsDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for (GameRecords records : gameRecordsPage.getContent()) {
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(records));
            json.put("user", usersDao.findAllById(records.getUid()));
            array.add(json);
        }
        object.put("list",array);
        object.put("total",gameRecordsPage.getTotalElements());
        return object;
    }
}
