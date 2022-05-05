package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.bootstrap.ServerWebSocket;
import com.telebott.moviesmanage.config.WebSocketConfig;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.CommodityVipOrder;
import com.telebott.moviesmanage.entity.GameCashInOrders;
import com.telebott.moviesmanage.entity.Videos;
import com.telebott.moviesmanage.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private CommodityDiamondOrderDao commodityDiamondOrderDao;
    @Autowired
    private CommodityVipOrderDao commodityVipOrderDao;
    @Autowired
    private GameCashInOrdersDao gameCashInOrdersDao;
    @Autowired
    private VideoOrdersDao videoOrdersDao;
    @Autowired
    private WithdrawalRecordsDao withdrawalRecordsDao;
    @Autowired
    private DeductionOrderDao deductionOrderDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private VideosDao videosDao;
    @Autowired
    private ShowPayOrdersDao showPayOrdersDao;

    public JSONObject diamond() {
        JSONObject object = new JSONObject();
        Long t = commodityDiamondOrderDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = commodityDiamondOrderDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = commodityDiamondOrderDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject vip() {
        JSONObject object = new JSONObject();
        Long t = commodityVipOrderDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = commodityVipOrderDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = commodityVipOrderDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject game() {
        JSONObject object = new JSONObject();
        Long t = gameCashInOrdersDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = gameCashInOrdersDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = gameCashInOrdersDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject withdrawal() {
        JSONObject object = new JSONObject();
        Long t = withdrawalRecordsDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = withdrawalRecordsDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = withdrawalRecordsDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject deduction() {
        JSONObject object = new JSONObject();
        Long t = deductionOrderDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = deductionOrderDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = deductionOrderDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject users() {
        JSONObject object = new JSONObject();
        Long t = usersDao.countAllByTime(TimeUtil.getTodayZero());
        Long y = usersDao.countAllByTime(TimeUtil.getBeforeDaysZero(1),TimeUtil.getTodayZero());
        Long all = usersDao.countAllByTime();
        object.put("all", all == null ? 0: all);
        object.put("t", t == null ? 0 : t);
        object.put("y", y == null ? 0 : y);
        return object;
    }

    public JSONObject other() {
        JSONObject object = new JSONObject();
        Long v = videosDao.count();
        Long o = ServerWebSocket.getOnline();
        Long l = authDao.countAllUser();
        object.put("v", v);
        object.put("o", o);
        object.put("y", l);
        return object;
    }
}
