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
public class RechargeCentreService {
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
    private BalanceOrdersDao balanceOrdersDao;
    @Autowired
    private OnlinePayDao onlinePayDao;
    @Autowired
    private OnlineOrderDao onlineOrderDao;
    @Autowired
    private ShowPayDao showPayDao;
    @Autowired
    private ShowPayOrdersDao showPayOrdersDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private SystemConfigService systemConfigService;

    public JSONObject getBalanceList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        int type = 0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<BalanceOrders> balanceOrdersPage;
        if (data != null){
            if (data.get("page") != null) page = Integer.parseInt(data.get("page").toString());
            if (data.get("limit") != null) limit = Integer.parseInt(data.get("limit").toString());
            if (data.get("type") != null && StringUtils.isNotEmpty(data.get("type").toString())) type = Integer.parseInt(data.get("type").toString());
            page--;
            if (page < 0) page =0;
            if (limit < 1){
                limit = 1;
            }
            if (data.get("sort") != null){
                if (data.get("sort").toString().equals("+id")){
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
                }else {
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
                }
            }else {
                pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            }

            if ((data.get("id") == null || StringUtils.isEmpty(data.get("id").toString())) && (data.get("status") == null || StringUtils.isEmpty(data.get("status").toString()))){
                balanceOrdersPage = balanceOrdersDao.findAll(pageable);
            }else if (data.get("id") != null && data.get("status") != null && StringUtils.isNotEmpty(data.get("status").toString())){
                int status = Integer.parseInt(data.get("status").toString());
                long id = Long.parseLong(data.get("id").toString());
                if (type == 0){
                    balanceOrdersPage = balanceOrdersDao.findAllByIdAndStatus(id,status, pageable);
                }else {
                    balanceOrdersPage = balanceOrdersDao.findAllByUidAndStatus(id,status,pageable);
                }
            }else if (data.get("id") != null){
                long id = Long.parseLong(data.get("id").toString());
                if (type == 0){
                    balanceOrdersPage = balanceOrdersDao.findAllById(id, pageable);
                }else {
                    balanceOrdersPage = balanceOrdersDao.findAllByUid(id,pageable);
                }
            }else {
                int status = 0;
                if (StringUtils.isNotEmpty(data.get("status").toString())){
                    status = Integer.parseInt(data.get("status").toString());
                }
                balanceOrdersPage = balanceOrdersDao.findAllByStatus(status,pageable);
            }
        }else {
            page--;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            balanceOrdersPage = balanceOrdersDao.findAll(pageable);
        }
        object.put("total",balanceOrdersPage.getTotalElements());
        JSONArray array = new JSONArray();
        for (BalanceOrders orders: balanceOrdersPage.getContent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",orders.getId());
            JSONObject user = new JSONObject();
            Users _user = usersDao.findAllById(orders.getUid());
            if (_user != null){
                user.put("id", _user.getId());
                user.put("nickname", _user.getNickname());
            }
            jsonObject.put("user", user);
            double b = orders.getAmount() / 100d;
            jsonObject.put("amount", String.format("%.2f",b));
            jsonObject.put("reason",orders.getReason());
            jsonObject.put("status",orders.getStatus());
            jsonObject.put("add_time",orders.getAddTime());
            jsonObject.put("update_time",orders.getUpdateTime());
            array.add(jsonObject);
        }
        object.put("list",array);
        return object;
    }

    public boolean deleteBalance(JSONObject data) {
        if (data != null && data.get("id") != null && StringUtils.isNotEmpty(data.getString("id"))){
            BalanceOrders orders = balanceOrdersDao.findAllById(Long.parseLong(data.getString("id")));
            if (orders != null){
                balanceOrdersDao.delete(orders);
                return true;
            }
        }
        return false;
    }

    public boolean updateBalance(JSONObject data) {
        if (data != null && data.get("id") != null){
            data.put("updateTime", System.currentTimeMillis());
            data.put("addTime", data.get("add_time"));
            data.put("amount", null);
            data.put("uid", null);
            BalanceOrders orders = balanceOrdersDao.findAllById(Long.parseLong(data.get("id").toString()));
            if (orders != null){
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(orders));
                for (Map.Entry<String, Object> entry: data.entrySet()) {
                    if (entry.getValue() != null){
                        object.put(entry.getKey(), entry.getValue());
                    }
                }
                orders = JSONObject.toJavaObject(object,BalanceOrders.class);
                balanceOrdersDao.save(orders);
                return true;
            }
        }
        return false;
    }

    public boolean addBalance(JSONObject data) {
        if (data != null && data.get("uid") != null && data.get("amount") != null){
            int status = 0;
            long addTime = System.currentTimeMillis();
            String reason = "后台充值";
            if (data.get("status") != null && StringUtils.isNotEmpty(data.get("status").toString())) status = Integer.parseInt(data.get("status").toString());
            if (data.get("add_time") != null && StringUtils.isNotEmpty(data.get("add_time").toString())) addTime = Long.parseLong(data.get("add_time").toString());
            if (data.get("reason") != null && StringUtils.isNotEmpty(data.get("reason").toString())) reason = data.getString("reason");
            BalanceOrders orders = new BalanceOrders();
            orders.setAddTime(addTime);
            orders.setUpdateTime(System.currentTimeMillis());
            orders.setReason(reason);
            orders.setStatus(status);
            orders.setAmount((long) (Double.parseDouble(data.getString("amount")) * 100));
            orders.setUid(Long.parseLong(data.get("uid").toString()));
            Users users = usersDao.findAllById(orders.getUid());
            if (users != null) {
                balanceOrdersDao.saveAndFlush(orders);
                return true;
            }
        }
        return false;
    }

    public JSONObject checkUser(JSONObject data) {
        JSONObject object = new JSONObject();
        if (data != null && data.get("id") != null && StringUtils.isNotEmpty(data.getString("id"))){
            Users users = usersDao.findAllById(Long.parseLong(data.get("id").toString()));
            if (users != null){
                object.put("user",users.getNickname());
            }
        }
        return object;
    }
}
