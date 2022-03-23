package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.entity.SystemUser;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public JSONObject list(SystemUser user) {
        JSONObject data = new JSONObject();
        return data;
    }
}
