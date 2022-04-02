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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PayService {
    @Autowired
    private OnlineOrderDao onlineOrderDao;
    @Autowired
    private OnlinePayDao onlinePayDao;
    @Autowired
    private ShowPayDao showPayDao;
    @Autowired
    private ShowPayOrdersDao showPayOrdersDao;
    @Autowired
    private CommodityDiamondOrderDao commodityDiamondOrderDao;
    @Autowired
    private CommodityGoldOrderDao commodityGoldOrderDao;
    @Autowired
    private CommodityVipOrderDao commodityVipOrderDao;
    @Autowired
    private OnlineOrderService onlineOrderService;

    public JSONObject getTypeList(JSONObject data) {
        JSONObject object = new JSONObject();
        List<ShowPay> showPays = showPayDao.findAll();
        object.put("list",showPays);
        return object;
    }
    private boolean _checkData(OnlinePay onlinePay){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(onlinePay));
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        onlinePay = onlinePayDao.findAllByTitle(onlinePay.getTitle());
        return onlinePay == null;
    }
    private OnlinePay  _changeData(OnlinePay onlinePay){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(onlinePay));
        onlinePay = onlinePayDao.findAllById(onlinePay.getId());
        if (onlinePay != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(onlinePay));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            if(onlinePay.getTitle().equals(data.getString("title"))){
                onlinePay = JSONObject.toJavaObject(object, OnlinePay.class);
            }else {
                onlinePay = onlinePayDao.findAllByTitle(data.getString("title"));
                if (onlinePay == null) {
                    onlinePay = JSONObject.toJavaObject(object, OnlinePay.class);
                }else {
                    onlinePay = null;
                }
            }
        }
        return onlinePay;
    }
    public JSONObject getOnlinePayList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<OnlinePay> payPage;
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
                payPage = onlinePayDao.findAllByTitleLike(data.getString("title"),pageable);
            }else {
                payPage = onlinePayDao.findAll(pageable);
            }
        }else {
            payPage = onlinePayDao.findAll(pageable);
        }
        object.put("list",payPage.getContent());
        object.put("total",payPage.getTotalElements());
        return object;
    }
    public boolean addOnlinePay(JSONObject data) {
        data.put("ctime", System.currentTimeMillis());
        data.put("utime", System.currentTimeMillis());
        OnlinePay onlinePay = JSONObject.toJavaObject(data,OnlinePay.class);
        if (_checkData(onlinePay)){
            onlinePayDao.saveAndFlush(onlinePay);
            return true;
        }
        return false;
    }
    public boolean updateOnlinePay(JSONObject data) {
        data.put("utime", System.currentTimeMillis());
        OnlinePay onlinePay = JSONObject.toJavaObject(data,OnlinePay.class);
        onlinePay = _changeData(onlinePay);
        if (onlinePay != null){
            onlinePayDao.saveAndFlush(onlinePay);
            return true;
        }
        return false;
    }
    public boolean deleteOnlinePay(JSONObject data) {
        if (data != null && data.get("id") != null){
            OnlinePay onlinePay = onlinePayDao.findAllById(data.getLong("id"));
            if (onlinePay != null){
                onlinePayDao.delete(onlinePay);
                return true;
            }
        }
        return false;
    }

    boolean _checkData(ShowPay showPay){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(showPay));
        System.out.println(data);
        for (Map.Entry<String, Object> entry: data.entrySet()) {
            if (!entry.getKey().equals("id") && entry.getValue() == null){
                return false;
            }
        }
        showPay = showPayDao.findAllByTitleOrMchId(showPay.getTitle(), showPay.getMchId());
        return showPay == null;
    }
    private ShowPay  _changeData(ShowPay showPay){
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(showPay));
        showPay = showPayDao.findAllById(showPay.getId());
        if (showPay != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(showPay));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            if(showPay.getTitle().equals(data.getString("title")) && Objects.equals(showPay.getMchId(), data.getString("mchId"))){
                showPay = JSONObject.toJavaObject(object, ShowPay.class);
            }else {
                if (showPay.getTitle().equals(data.getString("title"))){
                    showPay = showPayDao.findAllByMchId(data.getString("mchId"));
                    if (showPay == null){
                        showPay = JSONObject.toJavaObject(object, ShowPay.class);
                    }else {
                        showPay = null;
                    }
                }else{
                    showPay = showPayDao.findAllByTitle(data.getString("title"));
                    if (showPay == null) {
                        showPay = JSONObject.toJavaObject(object, ShowPay.class);

                    }else {
                        showPay = null;
                    }
                }
            }
        }
        return showPay;
    }
    public JSONObject getConfigPayList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<ShowPay> payPage;
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
                payPage = showPayDao.findAllByTitleLike(data.getString("title"),pageable);
            }else {
                payPage = showPayDao.findAll(pageable);
            }
        }else {
            payPage = showPayDao.findAll(pageable);
        }
        object.put("list",payPage.getContent());
        object.put("total",payPage.getTotalElements());
        return object;
    }
    public boolean addConfigPay(JSONObject data) {
        data.put("ctime", System.currentTimeMillis());
        data.put("utime", System.currentTimeMillis());
        ShowPay showPay = JSONObject.toJavaObject(data,ShowPay.class);
        if (_checkData(showPay)){
            showPayDao.saveAndFlush(showPay);
            return true;
        }
        return false;
    }
    public boolean updateConfigPay(JSONObject data) {
        data.put("utime", System.currentTimeMillis());
        ShowPay showPay = JSONObject.toJavaObject(data,ShowPay.class);
        showPay = _changeData(showPay);
        if (showPay != null){
            showPayDao.saveAndFlush(showPay);
            return true;
        }
        return false;
    }
    public boolean deleteConfigPay(JSONObject data) {
        if (data != null && data.get("id") != null){
            ShowPay showPay = showPayDao.findAllById(data.getLong("id"));
            if (showPay != null){
                showPayDao.delete(showPay);
                return true;
            }
        }
        return false;
    }

    public JSONObject getShowPayOrderList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<ShowPayOrders> ordersPage;
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
                ordersPage = showPayOrdersDao.findAllByOrderNoLike("%"+data.getString("title")+"%",pageable);
            }else {
                ordersPage = showPayOrdersDao.findAll(pageable);
            }
        }else {
            ordersPage = showPayOrdersDao.findAll(pageable);
        }
        object.put("list",ordersPage.getContent());
        object.put("total",ordersPage.getTotalElements());
        return object;
    }
    public boolean ShowPayOrderSuccess(JSONObject data) {
        if (data != null && data.getString("id") != null){
            ShowPayOrders order = showPayOrdersDao.findAllById(data.getLong("id"));
            if (order != null && order.getStatus() == 0){
                order.setStatus(1);
                showPayOrdersDao.saveAndFlush(order);
                onlineOrderService.handlerOrderNotify(order);
                return true;
            }
        }
        return false;
    }
    public boolean ShowPayOrderFail(JSONObject data) {
        if (data != null && data.getString("id") != null){
            ShowPayOrders order = showPayOrdersDao.findAllById(data.getLong("id"));
            if (order != null && order.getStatus() == 0){
                order.setStatus(2);
                showPayOrdersDao.saveAndFlush(order);
                onlineOrderService.handlerOrderNotifyFail(order);
                return true;
            }
        }
        return false;
    }

    public JSONObject getOnlinePayOrderList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<OnlineOrder> ordersPage;
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
                ordersPage = onlineOrderDao.findAllByOrderIdLikeOrOrderNoLike ("%"+data.getString("title")+"%","%"+data.getString("title")+"%",pageable);
            }else {
                ordersPage = onlineOrderDao.findAll(pageable);
            }
        }else {
            ordersPage = onlineOrderDao.findAll(pageable);
        }
        object.put("list",ordersPage.getContent());
        object.put("total",ordersPage.getTotalElements());
        return object;
    }
    public boolean OnlinePayOrderSuccess(JSONObject data) {
        if (data != null && data.getString("id") != null){
            OnlineOrder order = onlineOrderDao.findAllById(data.getLong("id"));
            if (order != null && order.getStatus() == 0){
                order.setStatus(1);
                onlineOrderDao.saveAndFlush(order);
                onlineOrderService.handlerOrderNotify(order);
                return true;
            }
        }
        return false;
    }
    public boolean OnlinePayOrderFail(JSONObject data) {
        if (data != null && data.getString("id") != null){
            OnlineOrder order = onlineOrderDao.findAllById(data.getLong("id"));
            if (order != null && order.getStatus() == 0){
                order.setStatus(2);
                onlineOrderDao.saveAndFlush(order);
                onlineOrderService.handlerOrderNotifyFail(order);
                return true;
            }
        }
        return false;
    }

    public JSONObject getPidList(JSONObject data) {
        JSONObject object = new JSONObject();
        List<OnlinePay> pays = onlinePayDao.findAll();
        object.put("list",pays);
        return object;
    }
}
