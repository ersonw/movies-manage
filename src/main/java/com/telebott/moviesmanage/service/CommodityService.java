package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.*;
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
    @Autowired
    private GameCashInDao gameCashInDao;

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
        return commodityVip == null;
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
            if(commodityVip.getTitle().equals(data.getString("title"))){
                commodityVip = JSONObject.toJavaObject(object, CommodityVip.class);
            }else {
                commodityVip = commodityVipDao.findAllByTitle(data.getString("title"));
                if (commodityVip == null) {
                    commodityVip = JSONObject.toJavaObject(object, CommodityVip.class);
                }else {
                    commodityVip = null;
                }
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
        return commodityDiamond == null;
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
            if (commodityDiamond.getDiamond() == data.getLong("diamond")){
                commodityDiamond = JSONObject.toJavaObject(object, CommodityDiamond.class);
            }else {
                commodityDiamond = commodityDiamondDao.findAllByDiamond(data.getLong("diamond"));
                if (commodityDiamond == null) {
                    commodityDiamond = JSONObject.toJavaObject(object, CommodityDiamond.class);
                }else {
                    commodityDiamond = null;
                }
            }
        }
        return commodityDiamond;
    }
    public boolean addCommodityDiamond(JSONObject data) {
//        System.out.println(data);
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
        CommodityDiamond commodityDiamond = JSONObject.toJavaObject(data,CommodityDiamond.class);
        commodityDiamond.setUtime(System.currentTimeMillis());
        if (commodityDiamond.getCtime() == 0){
            commodityDiamond.setCtime(System.currentTimeMillis());
        }
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
    public JSONObject getCommodityGoldList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<CommodityGold> goldPage;
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
                goldPage = commodityGoldDao.findAllById(data.getLong("title"),pageable);
            }else {
                goldPage = commodityGoldDao.findAll(pageable);
            }
        }else {
            goldPage = commodityGoldDao.findAll(pageable);
        }
        object.put("list",goldPage.getContent());
        object.put("total",goldPage.getTotalElements());
        return object;
    }
    private boolean _checkData(CommodityGold commodityGold){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityGold));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        commodityGold = commodityGoldDao.findAllByGold(commodityGold.getGold());
        return commodityGold == null;
    }
    private CommodityGold  _changeData(CommodityGold commodityGold){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(commodityGold));
        commodityGold = commodityGoldDao.findAllById(commodityGold.getId());
        if (commodityGold != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(commodityGold));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            if (commodityGold.getGold() == data.getLong("gold")){
                commodityGold = JSONObject.toJavaObject(object, CommodityGold.class);
            }else {
                commodityGold = commodityGoldDao.findAllByGold(data.getLong("gold"));
                if (commodityGold == null) {
                    commodityGold = JSONObject.toJavaObject(object, CommodityGold.class);
                }else {
                    commodityGold = null;
                }
            }
        }
        return commodityGold;
    }
    public boolean addCommodityGold(JSONObject data) {
        data.put("ctime", System.currentTimeMillis());
        data.put("utime", System.currentTimeMillis());
        CommodityGold commodityGold = JSONObject.toJavaObject(data,CommodityGold.class);
        if (_checkData(commodityGold)){
            commodityGoldDao.saveAndFlush(commodityGold);
            return true;
        }
        return false;
    }
    public boolean updateCommodityGold(JSONObject data) {
        data.put("utime", System.currentTimeMillis());
        CommodityGold commodityGold = JSONObject.toJavaObject(data,CommodityGold.class);
        commodityGold = _changeData(commodityGold);
        if (commodityGold != null){
            commodityGoldDao.saveAndFlush(commodityGold);
            return true;
        }
        return false;
    }
    public boolean deleteCommodityGold(JSONObject data) {
        if (data != null && data.get("id") != null){
            CommodityGold commodityGold = commodityGoldDao.findAllById(data.getLong("id"));
            if (commodityGold != null){
                commodityGoldDao.delete(commodityGold);
                return true;
            }
        }
        return false;
    }

    public JSONObject getCommodityGameList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<GameCashIn> cashInPage;
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
                cashInPage = gameCashInDao.findAllById(data.getLong("title"),pageable);
            }else {
                cashInPage = gameCashInDao.findAll(pageable);
            }
        }else {
            cashInPage = gameCashInDao.findAll(pageable);
        }
        object.put("list",cashInPage.getContent());
        object.put("total",cashInPage.getTotalElements());
        return object;
    }
    private boolean _checkData(GameCashIn gameCashIn){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(gameCashIn));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        gameCashIn = gameCashInDao.findAllByAmount(gameCashIn.getAmount());
        return gameCashIn == null;
    }
    private GameCashIn  _changeData(GameCashIn gameCashIn){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(gameCashIn));
        gameCashIn = gameCashInDao.findAllById(gameCashIn.getId());
        if (gameCashIn != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(gameCashIn));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            if (gameCashIn.getAmount() == data.getLong("amount")){
                gameCashIn = JSONObject.toJavaObject(object, GameCashIn.class);
            }else {
                gameCashIn = gameCashInDao.findAllByAmount(data.getLong("amount"));
                if (gameCashIn == null) {
                    gameCashIn = JSONObject.toJavaObject(object, GameCashIn.class);
                }else {
                    gameCashIn = null;
                }
            }
        }
        return gameCashIn;
    }

    public boolean addCommodityGame(JSONObject data) {
        GameCashIn gameCashIn = JSONObject.toJavaObject(data,GameCashIn.class);
        gameCashIn.setUpdateTime(System.currentTimeMillis());
        gameCashIn.setAddTime(System.currentTimeMillis());
        if (_checkData(gameCashIn)){
            gameCashInDao.saveAndFlush(gameCashIn);
            return true;
        }
        return false;
    }

    public boolean updateCommodityGame(JSONObject data) {
        GameCashIn gameCashIn = JSONObject.toJavaObject(data,GameCashIn.class);
        gameCashIn.setUpdateTime(System.currentTimeMillis());
        if (gameCashIn.getAddTime() == 0){
            gameCashIn.setAddTime(System.currentTimeMillis());
        }
        gameCashIn = _changeData(gameCashIn);
        if (gameCashIn != null){
            gameCashInDao.saveAndFlush(gameCashIn);
            return true;
        }
        return false;
    }

    public boolean deleteCommodityGame(JSONObject data) {
        GameCashIn gameCashIn = JSONObject.toJavaObject(data,GameCashIn.class);
        if (gameCashIn != null){
            gameCashIn = gameCashInDao.findAllById(gameCashIn.getId());
            gameCashInDao.delete(gameCashIn);
            return true;
        }
        return false;
    }
}
