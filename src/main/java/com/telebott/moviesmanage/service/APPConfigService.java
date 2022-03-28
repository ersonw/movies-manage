package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.MoblieConfigDao;
import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.MoblieConfig;
import com.telebott.moviesmanage.entity.SystemConfig;
import com.telebott.moviesmanage.entity.Users;
import com.telebott.moviesmanage.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class APPConfigService {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private MoblieConfigDao moblieConfigDao;
    @Autowired
    private SystemConfigDao systemConfigDao;

    public JSONObject getAPPConfigList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<SystemConfig> systemConfigPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            if (data.get("sort") != null){
                if (data.getString("sort").equals("+id")){
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
                }else {
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
                }
            }
            if (StringUtils.isNotEmpty(data.getString("title"))){
                title = "%"+data.getString("title")+"%";
                systemConfigPage = systemConfigDao.findAllByNameLikeOrDesLike(title,title,pageable);
            }else {
                systemConfigPage = systemConfigDao.findAll(pageable);
            }
        }else {
            systemConfigPage = systemConfigDao.findAll(pageable);
        }
        object.put("total", systemConfigPage.getTotalElements());
        object.put("list",systemConfigPage.getContent());
        return object;
    }
    private SystemConfig _change(JSONObject object){
        SystemConfig config = systemConfigDao.findAllById(object.getLong("id"));
        if (config != null){
            JSONObject _config = JSONObject.parseObject(JSONObject.toJSONString(config));
            for (Map.Entry<String, Object> entry: object.entrySet()) {
                if (entry.getValue() != null){
                    _config.put(entry.getKey(), entry.getValue());
                }
            }
            config = JSONObject.toJavaObject(_config, SystemConfig.class);
            return config;
        }
        return null;
    }
    private MoblieConfig _changeMobile(JSONObject object){
        MoblieConfig config = moblieConfigDao.findAllById(object.getLong("id"));
        if (config != null){
            JSONObject _config = JSONObject.parseObject(JSONObject.toJSONString(config));
            for (Map.Entry<String, Object> entry: object.entrySet()) {
                if (entry.getValue() != null){
                    _config.put(entry.getKey(), entry.getValue());
                }
            }
            config = JSONObject.toJavaObject(_config, MoblieConfig.class);
            return config;
        }
        return null;
    }
    public boolean updateAPPConfigList(JSONObject data) {
        if (data.get("id") != null){
            data.put("ctime",System.currentTimeMillis());
            data.put("name",null);
            SystemConfig config = _change(data);
            if (config != null){
                systemConfigDao.saveAndFlush(config);
                return true;
            }
        }
        return false;
    }

    public JSONObject updateConfig() {
        List<MoblieConfig> configs = moblieConfigDao.findAll();
        if (configs.size() > 0){
            MoblieConfig config = configs.get(configs.size()-1);
            MD5Util md5Util = new MD5Util();
            config.setHash(md5Util.getMD5(Long.toString(System.currentTimeMillis())));
            moblieConfigDao.saveAndFlush(config);
        }
        return new JSONObject();
    }

    public JSONObject getAPPConfigVersionList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<MoblieConfig> moblieConfigPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            if (data.get("sort") != null){
                if (data.getString("sort").equals("+id")){
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
                }else {
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
                }
            }
            if (StringUtils.isNotEmpty(data.getString("title"))){
                title = "%"+data.getString("title")+"%";
                moblieConfigPage = moblieConfigDao.findAllByVersionLike(title,pageable);
            }else {
                moblieConfigPage = moblieConfigDao.findAll(pageable);
            }
        }else {
            moblieConfigPage = moblieConfigDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for ( MoblieConfig config :moblieConfigPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(config));
//            jsonObject.put("forces", config.getForces() == 1);
//            jsonObject.put("autoLogin", config.getAutoLogin() == 1);
            array.add(jsonObject);
        }
        object.put("total", moblieConfigPage.getTotalElements());
        object.put("list",array);
        return object;
    }

    public boolean updateAPPConfigVersionList(JSONObject data) {
        if (data != null && StringUtils.isNotEmpty(data.getString("id"))){
            data.put("updateTime",System.currentTimeMillis());
            data.put("addTime",null);
            MoblieConfig config = _changeMobile(data);
            if (config != null){
                moblieConfigDao.saveAndFlush(config);
                return true;
            }
        }
        return false;
    }

    public boolean deleteAPPConfigVersionList(JSONObject data) {
        if (data != null && data.get("id") != null){
            MoblieConfig config = moblieConfigDao.findAllById(data.getLong("id"));
            if (config != null){
                moblieConfigDao.delete(config);
                return true;
            }
        }
        return false;
    }

    public boolean addAPPConfigVersionList(JSONObject data) {
        if (data != null && StringUtils.isNotEmpty(data.getString("version"))){
            MD5Util md5Util = new MD5Util();
            long time = System.currentTimeMillis();
//            boolean forces = data.getBoolean("forces");
//            boolean autoLogin = data.getBoolean("forces");
            MoblieConfig config = new MoblieConfig();
            config.setVersion(data.getString("version"));
            config.setHash(md5Util.getMD5(Long.toString(time)));
            config.setAddTime(time);
            config.setUpdateTime(time);
            config.setForces(data.getInteger("version"));
            config.setAutoLogin(data.getInteger("version"));
            moblieConfigDao.saveAndFlush(config);
            return true;
        }
        return false;
    }
}
