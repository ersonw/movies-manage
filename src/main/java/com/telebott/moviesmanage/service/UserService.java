package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.MoblieConfig;
import com.telebott.moviesmanage.entity.SmsRecords;
import com.telebott.moviesmanage.entity.Users;
import com.telebott.moviesmanage.util.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private UserFollowsDao userFollowsDao;
    @Autowired
    private BalanceOrdersDao balanceOrdersDao;
    @Autowired
    private DiamondRecordsDao diamondRecordsDao;
    @Autowired
    private GoldRecordsDao goldRecordsDao;
    @Autowired
    private SmsRecordsDao smsRecordsDao;

    public void _save(Users users){
        usersDao.saveAndFlush(users);
    }
    public void _push(Users users){
        authDao.pushUser(users);
    }
    public void _saveAndPush(Users _user){
        _user.setUtime(System.currentTimeMillis() / 1000);
        if (isUser(_user.getId())){
            _save(_user);
        }
        _push(_user);
    }
    public String _getSalt(){
        return RandomStringUtils.randomAlphanumeric(32);
    }
    public Users _change(Users _user){
        if (_user != null && isUser(_user.getId())){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(_user));
            Users user = usersDao.findAllById(_user.getId());
            if (user != null){
                JSONObject _token = JSONObject.parseObject(JSONObject.toJSONString(user));
                for (Map.Entry<String, Object> entry: object.entrySet()) {
                    if (entry.getValue() != null){
                        _token.put(entry.getKey(), entry.getValue());
                    }
                }
                _user = JSONObject.toJavaObject(_token,Users.class);
                return _user;
            }
        }
        return null;
    }
    private boolean isUser(String token){
        return authDao.findUserByToken(token) != null;
    }
    private boolean isUser(long id){
        return usersDao.findAllById(id) != null;
    }
    public JSONObject getBalance(Users user) {
        JSONObject object = new JSONObject();
        long amount = balanceOrdersDao.countAllByUidAndStatus(user.getId(),1);
        if (amount > 0){
            object.put("balance",balanceOrdersDao.countAllByBalance(user.getId()));
        }else {
            object.put("balance",0);
        }
        return object;
    }
    public long getDiamond(Users user) {
        long amount = diamondRecordsDao.countAllByUidAndStatus(user.getId(),1);
        if (amount > 0){
            return diamondRecordsDao.countAllByBalance(user.getId());
        }
        return amount;
    }
    public long getGold(Users user) {
        long amount = goldRecordsDao.countAllByUidAndStatus(user.getId(),1);
        if (amount > 0){
            return goldRecordsDao.countAllByBalance(user.getId());
        }
        return amount;
    }

    public JSONObject getList(JSONObject data) {
//        JSONObject data = JSONObject.parseObject(d);
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Users> usersPage;
        if (data != null){
            if (data.get("page") != null) page = Integer.parseInt(data.get("page").toString());
            if (data.get("limit") != null) limit = Integer.parseInt(data.get("limit").toString());
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
            if ((data.get("title") == null || StringUtils.isEmpty(data.get("title").toString())) && (data.get("status") == null || StringUtils.isEmpty(data.get("status").toString()))){
                usersPage = usersDao.findAll(pageable);
            }else if (data.get("title") != null && data.get("status") != null && StringUtils.isNotEmpty(data.get("status").toString())){
                int status = Integer.parseInt(data.get("status").toString());
                String title = "%"+data.get("title").toString()+"%";
                usersPage = usersDao.findAllByNicknamelike(title,status,pageable);
            }else if (data.get("title") != null){
                String title = "%"+data.get("title").toString()+"%";
                usersPage = usersDao.findAllByNicknameLikeOrPhoneLikeOrUidLike(title,title,title,pageable);
            }else {
                int status = 0;
                if (StringUtils.isNotEmpty(data.get("status").toString())){
                    status = Integer.parseInt(data.get("status").toString());
                }
                usersPage = usersDao.findAllByStatus(status,pageable);
            }
        }else {
            usersPage = usersDao.findAll(pageable);
        }
        object.put("total",usersPage.getTotalElements());
        JSONArray array = new JSONArray();
        for (Users user: usersPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(user));
            jsonObject.put("gold", getGold(user));
            jsonObject.put("diamond", getDiamond(user));
            jsonObject.put("share", getShare(user.getId()));
            jsonObject.put("follws", userFollowsDao.countAllByUid(user.getId()));
            long balance = Long.parseLong(getBalance(user).get("balance").toString());
//            jsonObject.put("balance", 0);
            double b = balance / 100d;
            jsonObject.put("balance", String.format("%.2f",b));
            jsonObject.put("status",user.getStatus());
            jsonObject.put("phone",user.getPhone());
            jsonObject.put("invite",user.getInvite());
            jsonObject.put("identifier",user.getIdentifier());
            jsonObject.put("uid",user.getUid());
            jsonObject.put("superior","无");
            if (user.getSuperior() > 0){
                Users _user = usersDao.findAllById(user.getSuperior());
                if (_user != null) jsonObject.put("superior",_user.getNickname());
            }
            jsonObject.put("expireds",user.getExpireds());
            jsonObject.put("bk_image",user.getBkImage());
            jsonObject.put("avatar",user.getAvatar());
            array.add(jsonObject);
        }
        object.put("list",array);
        return object;
    }

    public boolean delete(JSONObject data) {
        if (data != null && data.get("id") != null){
            Users users = usersDao.findAllById(Long.parseLong(data.get("id").toString()));
            if (users != null){
                usersDao.delete(users);
                authDao.removeUser(users);
                return true;
            }
        }
        return false;
    }

    public boolean update(JSONObject data) {
//        System.out.println(data);
        if (data != null){
            data.put("utime",System.currentTimeMillis());
            Users user = JSONObject.toJavaObject(data,Users.class);
            if  (user != null){
                Users users = usersDao.findAllById(user.getId());
                if (users != null){
                    users = _change(user);
                    if (users != null){
                        usersDao.saveAndFlush(users);
                        authDao.removeUser(users);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private long getShare(long uid){
        return usersDao.countAllBySuperior(uid);
    }
    public JSONObject superior(long uid) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        if (uid > 0){
            Users user = usersDao.findAllById(uid);
            if (user != null){
                Users _superior = usersDao.findAllById(user.getSuperior());
                if (_superior != null){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", _superior.getId());
                    jsonObject.put("nickname", _superior.getNickname());
                    jsonObject.put("avatar", _superior.getAvatar());
                    jsonObject.put("phone", _superior.getPhone());
                    jsonObject.put("share", getShare(_superior.getId()));
                    array.add(jsonObject);
                }
            }
        }
        object.put("list",array);
        return object;
    }

    public JSONObject share(long uid) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        if (uid > 0){
            Users user = usersDao.findAllById(uid);
            if (user != null){
                List<Users> usersList = usersDao.findAllBySuperior(user.getId());
                for (Users users: usersList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", users.getId());
                    jsonObject.put("nickname", users.getNickname());
                    jsonObject.put("avatar", users.getAvatar());
                    jsonObject.put("phone", users.getPhone());
                    jsonObject.put("share", getShare(users.getId()));
                    array.add(jsonObject);
                }
            }
        }
        object.put("list",array);
        return object;
    }

    public JSONObject getSmsRecordsList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<SmsRecords> smsRecordsPage;
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
                smsRecordsPage = smsRecordsDao.findAllByNumberLike(title,pageable);
            }else {
                smsRecordsPage = smsRecordsDao.findAll(pageable);
            }
        }else {
            smsRecordsPage = smsRecordsDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for ( SmsRecords records : smsRecordsPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(records));
            array.add(jsonObject);
        }
        object.put("total", smsRecordsPage.getTotalElements());
        object.put("list",array);
        return object;
    }

    public boolean clean() {
        return false;
    }

    public JSONObject vipList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 0;
        int limit = 20;
        long expire = System.currentTimeMillis();
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Users> usersPage;
        if (data != null){
            if (data.get("page") != null) page = Integer.parseInt(data.get("page").toString());
            if (data.get("limit") != null) limit = Integer.parseInt(data.get("limit").toString());
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
            if ((data.get("title") == null || StringUtils.isEmpty(data.get("title").toString()))){
                usersPage = usersDao.findAll(expire,pageable);
            }else {
                String title = "%"+data.get("title").toString()+"%";
                usersPage = usersDao.findAll(title,expire,pageable);
            }
        }else {
            usersPage = usersDao.findAll(expire,pageable);
        }
        object.put("total",usersPage.getTotalElements());
        JSONArray array = new JSONArray();
        for (Users user: usersPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(user));
            jsonObject.put("gold", getGold(user));
            jsonObject.put("diamond", getDiamond(user));
            jsonObject.put("share", getShare(user.getId()));
            jsonObject.put("follws", userFollowsDao.countAllByUid(user.getId()));
            long balance = Long.parseLong(getBalance(user).get("balance").toString());
            double b = balance / 100d;
            jsonObject.put("balance", String.format("%.2f",b));
            jsonObject.put("status",user.getStatus());
            jsonObject.put("phone",user.getPhone());
            jsonObject.put("invite",user.getInvite());
            jsonObject.put("identifier",user.getIdentifier());
            jsonObject.put("uid",user.getUid());
            jsonObject.put("superior","无");
            if (user.getSuperior() > 0){
                Users _user = usersDao.findAllById(user.getSuperior());
                if (_user != null) jsonObject.put("superior",_user.getNickname());
            }
            jsonObject.put("expireds",user.getExpireds());
            jsonObject.put("bk_image",user.getBkImage());
            jsonObject.put("avatar",user.getAvatar());
            array.add(jsonObject);
        }
        object.put("list",array);
        return object;
    }

    public boolean vipAdd(JSONObject data) {
        Users user = usersDao.findAllById(data.getLong("id"));
        long expire = TimeUtil.getAfterDaysZero(1);
        if (StringUtils.isNotEmpty(data.getString("date"))){
            expire = TimeUtil.getDateZero(data.getString("date"));
            if (expire < System.currentTimeMillis()){
                expire = TimeUtil.getAfterDaysZero(1);
            }
        }
        if (user!=null){
            user.setExpireds(expire);
            _saveAndPush(user);
            return true;
        }
        return false;
    }

    public boolean cleanVip() {
        return false;
    }
}
