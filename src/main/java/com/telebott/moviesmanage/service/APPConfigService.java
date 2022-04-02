package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.CarouselDao;
import com.telebott.moviesmanage.dao.MoblieConfigDao;
import com.telebott.moviesmanage.dao.SystemConfigDao;
import com.telebott.moviesmanage.entity.*;
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
import java.util.Objects;

@Service
public class APPConfigService {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private MoblieConfigDao moblieConfigDao;
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private CarouselDao carouselDao;

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
            pageable = VideosService.getPageable(data, page, limit, pageable);
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
            pageable = VideosService.getPageable(data, page, limit, pageable);
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
            config.setText(data.getString("text"));
            config.setHash(md5Util.getMD5(Long.toString(time)));
            config.setAddTime(time);
            config.setUpdateTime(time);
            config.setForces(data.getInteger("forces"));
            config.setAutoLogin(data.getInteger("autoLogin"));
            moblieConfigDao.saveAndFlush(config);
            return true;
        }
        return false;
    }
    boolean _checkData(Carousel carousel){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(carousel));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        carousel = carouselDao.findAllByName(carousel.getName());
        return carousel == null;
    }
    private Carousel  _changeData(Carousel carousel){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(carousel));
        carousel = carouselDao.findAllById(carousel.getId());
        if (carousel != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(carousel));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            if(data.get("name") != null && Objects.equals(data.getString("name"), carousel.getName())){
                carousel = JSONObject.toJavaObject(object, Carousel.class);
            }else {
                carousel = carouselDao.findAllByName(data.getString("name"));
                if (carousel == null){
                    carousel = JSONObject.toJavaObject(object, Carousel.class);
                }else {
                    carousel = null;
                }
            }
        }
        return carousel;
    }
    public JSONObject getCarouselList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Carousel> carouselPage;
        JSONArray array = new JSONArray();
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = VideosService.getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty(data.getString("title"))){
                carouselPage = carouselDao.findAllByNameLike("%"+data.getString("title")+"%",pageable);
            }else {
                carouselPage = carouselDao.findAll(pageable);
            }
        }else {
            carouselPage = carouselDao.findAll(pageable);
        }
        object.put("list",carouselPage.getContent());
        object.put("total",carouselPage.getTotalElements());
        return object;
    }

    public boolean updateCarousel(JSONObject data) {
        data.put("addTime",System.currentTimeMillis());
        Carousel carousel = JSONObject.toJavaObject(data,Carousel.class);
        carousel = _changeData(carousel);
        if (carousel != null){
            carouselDao.saveAndFlush(carousel);
            return true;
        }
        return false;
    }

    public boolean addCarousel(JSONObject data) {
        data.put("addTime",System.currentTimeMillis());
        Carousel carousel = JSONObject.toJavaObject(data,Carousel.class);
        if (_checkData(carousel)){
            carouselDao.saveAndFlush(carousel);
        }
        return false;
    }

    public boolean deleteCarousel(JSONObject data) {
        if (data != null && data.get("id") != null){
            Carousel carousel = carouselDao.findAllById(data.getLong("id"));
            if (carousel != null){
                carouselDao.delete(carousel);
                return true;
            }
        }
        return false;
    }
}
