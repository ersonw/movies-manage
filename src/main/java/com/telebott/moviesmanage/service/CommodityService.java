package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.CommodityDiamond;
import com.telebott.moviesmanage.entity.CommodityVip;
import com.telebott.moviesmanage.entity.EditorRecommends;
import com.telebott.moviesmanage.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommodityService {
    @Autowired
    private CommodityVipDao commodityVipDao;
    @Autowired
    private CommodityVipOrderDao commodityVipOrderDao;
    @Autowired
    private CommodityDiamondDao commodityDiamondDao;
    @Autowired
    private CommodityDiamondOrderDao commodityDiamondOrderDao;
    @Autowired
    private CommodityGoldDao commodityGoldDao;
    @Autowired
    private CommodityGoldOrderDao commodityGoldOrderDao;

    public JSONObject getCommodityVipList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<CommodityVip> vipPage;
        JSONArray array = new JSONArray();
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = VideosService.getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty("%"+data.getString("title")+"%")){
                vipPage = commodityVipDao.findAllByTitleLike(data.getString("title"),pageable);
            }else {
                vipPage = commodityVipDao.findAll(pageable);
            }
        }else {
            vipPage = commodityVipDao.findAll(pageable);
        }
        object.put("list",vipPage.getContent());
        object.put("total",vipPage.getTotalElements());
        return object;
    }
    private boolean _checkData(CommodityVip commodityVip){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityVip));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        commodityVip = commodityVipDao.findAllByTitle(commodityVip.getTitle());
        if (commodityVip != null) return false;
        return true;
    }
    private CommodityVip  _changeData(CommodityVip commodityVip){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityVip));
        commodityVip = commodityVipDao.findAllById(commodityVip.getId());
        if (commodityVip != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(commodityVip));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            commodityVip = commodityVipDao.findAllByTitle(commodityVip.getTitle());
            if (commodityVip == null) {
                commodityVip = JSONObject.toJavaObject(object, CommodityVip.class);
            }else {
                commodityVip = null;
            }
        }
        return commodityVip;
    }
    public boolean addCommodityVip(JSONObject data) {
        data.put("ctime", System.currentTimeMillis());
        data.put("utime", System.currentTimeMillis());
        data.put("type", 0);
        CommodityVip commodityVip = JSONObject.toJavaObject(data,CommodityVip.class);
        if (_checkData(commodityVip)){
            commodityVipDao.saveAndFlush(commodityVip);
            return true;
        }
        return false;
    }
    public boolean updateCommodityVip(JSONObject data) {
        data.put("utime", System.currentTimeMillis());
        CommodityVip commodityVip = JSONObject.toJavaObject(data,CommodityVip.class);
        commodityVip = _changeData(commodityVip);
        if (commodityVip != null){
            commodityVipDao.saveAndFlush(commodityVip);
            return true;
        }
        return false;
    }
    public boolean deleteCommodityVip(JSONObject data) {
        if (data != null && data.get("id") != null){
            CommodityVip commodityVip = commodityVipDao.findAllById(data.getLong("id"));
            if (commodityVip != null){
                commodityVipDao.delete(commodityVip);
                return true;
            }
        }
        return false;
    }
    public JSONObject getCommodityDiamondList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<CommodityDiamond> diamondPage;
        JSONArray array = new JSONArray();
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = VideosService.getPageable(data, page, limit, pageable);
            if (data.get("title") != null &&
                    StringUtils.isNotEmpty(data.getString("title")) &&
                    VideosService.isNumberString(data.getString("title"))
            ){
                diamondPage = commodityDiamondDao.findAllById(data.getLong("title"),pageable);
            }else {
                diamondPage = commodityDiamondDao.findAll(pageable);
            }
        }else {
            diamondPage = commodityDiamondDao.findAll(pageable);
        }
        object.put("list",diamondPage.getContent());
        object.put("total",diamondPage.getTotalElements());
        return object;
    }
    private boolean _checkData(CommodityDiamond commodityDiamond){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityDiamond));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        commodityDiamond = commodityDiamondDao.findAllByDiamond(commodityDiamond.getDiamond());
        if (commodityDiamond != null) return false;
        return true;
    }
    private CommodityDiamond  _changeData(CommodityDiamond commodityDiamond){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityDiamond));
        commodityDiamond = commodityDiamondDao.findAllById(commodityDiamond.getId());
        if (commodityDiamond != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(commodityDiamond));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            commodityDiamond = commodityDiamondDao.findAllByDiamond(commodityDiamond.getDiamond());
            if (commodityDiamond == null) {
                commodityDiamond = JSONObject.toJavaObject(object, CommodityDiamond.class);
            }else {
                commodityDiamond = null;
            }
        }
        return commodityDiamond;
    }
    public boolean addCommodityDiamond(JSONObject data) {
        data.put("ctime", System.currentTimeMillis());
        data.put("utime", System.currentTimeMillis());
        CommodityDiamond commodityDiamond = JSONObject.toJavaObject(data,CommodityDiamond.class);
        if (_checkData(commodityDiamond)){
            commodityDiamondDao.saveAndFlush(commodityDiamond);
            return true;
        }
        return false;
    }
    public boolean updateCommodityDiamond(JSONObject data) {
        data.put("utime", System.currentTimeMillis());
        CommodityDiamond commodityDiamond = JSONObject.toJavaObject(data,CommodityDiamond.class);
        commodityDiamond = _changeData(commodityDiamond);
        if (commodityDiamond != null){
            commodityDiamondDao.saveAndFlush(commodityDiamond);
            return true;
        }
        return false;
    }
    public boolean deleteCommodityDiamond(JSONObject data) {
        if (data != null && data.get("id") != null){
            CommodityDiamond commodityDiamond = commodityDiamondDao.findAllById(data.getLong("id"));
            if (commodityDiamond != null){
                commodityDiamondDao.delete(commodityDiamond);
                return true;
            }
        }
        return false;
    }
}
