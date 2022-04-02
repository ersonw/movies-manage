package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.UsersDao;
import com.telebott.moviesmanage.dao.WithdrawalCardsDao;
import com.telebott.moviesmanage.dao.WithdrawalRecordsDao;
import com.telebott.moviesmanage.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WithdrawService {
    @Autowired
    private WithdrawalRecordsDao withdrawalRecordsDao;
    @Autowired
    private WithdrawalCardsDao withdrawalCardsDao;
    @Autowired
    private UsersDao usersDao;
    public JSONObject getList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<WithdrawalRecords> recordsPage;
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
                recordsPage = withdrawalRecordsDao.findAllByorderNoLike("%"+data.getString("title")+"%",pageable);
            }else {
                recordsPage = withdrawalRecordsDao.findAll(pageable);
            }
        }else {
            recordsPage = withdrawalRecordsDao.findAll(pageable);
        }
        for (WithdrawalRecords record: recordsPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(record));
            jsonObject.put("user", usersDao.findAllById(record.getUid()));
            jsonObject.put("card", withdrawalCardsDao.findAllById(record.getCid()));
            array.add(jsonObject);
        }
        object.put("list", array);
        object.put("total",recordsPage.getTotalElements());
        return object;
    }

    public boolean handleShenHe(JSONObject data, String user) {
        if (data != null && data.get("id") != null){
            WithdrawalRecords record = withdrawalRecordsDao.findAllById(data.getLong("id"));
            if (record != null && record.getStatus() == 0){
                record.setUpdateTime(System.currentTimeMillis());
                record.setStatus(1);
                withdrawalRecordsDao.saveAndFlush(record);
                return true;
            }
        }
        return false;
    }

    public boolean update(JSONObject data, String user) {
        if (data != null && data.get("id") != null){
            WithdrawalRecords record = withdrawalRecordsDao.findAllById(data.getLong("id"));
            if (record != null && record.getStatus() == 1){
                record.setStatus(2);
                record.setUpdateTime(System.currentTimeMillis());
                record.setReason(data.getString("reason"));
                withdrawalRecordsDao.saveAndFlush(record);
                return true;
            }
        }
        return false;
    }

    public boolean handleBack(JSONObject data) {
        if (data != null && data.get("id") != null){
            WithdrawalRecords record = withdrawalRecordsDao.findAllById(data.getLong("id"));
            if (record != null && record.getStatus() == 1){
                record.setUpdateTime(System.currentTimeMillis());
                record.setReason(data.getString("reason"));
                record.setStatus(0);
                withdrawalRecordsDao.saveAndFlush(record);
                return true;
            }
        }
        return false;
    }

    public boolean del(JSONObject data, SystemUser user) {
        if (data != null && data.get("id") != null){
            WithdrawalRecords record = withdrawalRecordsDao.findAllById(data.getLong("id"));
            if (record != null && (record.getStatus() == 2 || record.getStatus() == -2)){
                withdrawalRecordsDao.delete(record);
                return true;
            }
        }
        return false;
    }

    public JSONObject getCardList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<WithdrawalCards> cardsPage;
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
                String title = "%"+data.getString("title")+"%";
                cardsPage = withdrawalCardsDao.findAllByNameLikeOrBankLikeOrCodeLike(title,title,title,pageable);
            }else {
                cardsPage = withdrawalCardsDao.findAll(pageable);
            }
        }else {
            cardsPage = withdrawalCardsDao.findAll(pageable);
        }
        for (WithdrawalCards card: cardsPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(card));
            jsonObject.put("user", usersDao.findAllById(card.getUid()));
            array.add(jsonObject);
        }
        object.put("list", array);
        object.put("total",cardsPage.getTotalElements());
        return object;
    }

    public boolean updateCard(JSONObject data,  SystemUser user) {
        if (data != null && data.get("id") != null){
            WithdrawalCards card = withdrawalCardsDao.findAllById(data.getLong("id"));
            if (card != null){
                if (data.get("name") != null && StringUtils.isNotEmpty(data.getString("name"))) card.setName(data.getString("name"));
                if (data.get("bank") != null && StringUtils.isNotEmpty(data.getString("bank"))) card.setBank(data.getString("bank"));
                if (data.get("code") != null && StringUtils.isNotEmpty(data.getString("code"))) card.setCode(data.getString("code"));
                card.setUpdateTime(System.currentTimeMillis());
                withdrawalCardsDao.saveAndFlush(card);
                return true;
            }
        }
        return false;
    }

    public boolean delCard(JSONObject data,  SystemUser user) {
        if (data != null && data.get("id") != null){
            WithdrawalCards card = withdrawalCardsDao.findAllById(data.getLong("id"));
            if (card != null){
                withdrawalCardsDao.delete(card);
                return true;
            }
        }
        return false;
    }
}
