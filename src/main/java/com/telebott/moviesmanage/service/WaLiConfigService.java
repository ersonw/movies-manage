package com.telebott.moviesmanage.service;

import com.telebott.moviesmanage.dao.WaLiConfigDao;
import com.telebott.moviesmanage.entity.WaLiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaLiConfigService {
    @Autowired
    private WaLiConfigDao waLiConfigDao;
    public String getValueByName(String name) {
        WaLiConfig config = waLiConfigDao.findAllByName(name);
        if (config != null) {
            return config.getVal();
        }
        return null;
    }
}
