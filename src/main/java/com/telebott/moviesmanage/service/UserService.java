package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private VideoRecommendsDao videoRecommendsDao;
    @Autowired
    private UserFollowsDao userFollowsDao;
    @Autowired
    private UserPostsDao userPostsDao;
    @Autowired
    private BalanceOrdersDao balanceOrdersDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private ShareRecordsDao shareRecordsDao;

    public void _save(Users users){
        usersDao.saveAndFlush(users);
    }
    public void _push(Users users){
        authDao.pushUser(users);
    }
    public Users _getInviteOwner(String invite){
        return usersDao.findAllByInvite(invite);
    }
    public void _saveAndPush(Users _user){
        _user.setUtime(System.currentTimeMillis() / 1000);
        if (isUser(_user.getId())){
            _save(_user);
        }
        _push(_user);
    }
    public Users _getById(long id){
        return usersDao.findAllById(id);
    }
    public String _getSalt(){
        return RandomStringUtils.randomAlphanumeric(32);
    }
    public String _getInvite(){
        String invite = RandomStringUtils.randomAlphanumeric(6);
        Users users = usersDao.findAllByInvite(invite);
        if (users != null){
            return _getInvite();
        }
        return invite;
    }
    public Users _change(JSONObject object){
        Users _user = JSONObject.toJavaObject(object,Users.class);
        if (_user != null && isUser(_user.getToken())){
            JSONObject _token = JSONObject.parseObject(JSONObject.toJSONString(authDao.findUserByToken(_user.getToken())));
            for (Map.Entry<String, Object> entry: object.entrySet()) {
//                if (StringUtils.isNotEmpty(entry.getValue().toString())){
                    _token.put(entry.getKey(), entry.getValue());
//                }
            }
            _user = JSONObject.toJavaObject(_token,Users.class);
            _saveAndPush(_user);
            return _user;
        }
        return null;
    }
    public boolean isUser(Users users){
        if (users.getId() > 0){
            return isUser(users.getId());
        }else {
            return isUser(users.getToken());
        }
    }
    private boolean isUser(String token){
        return authDao.findUserByToken(token) != null;
    }
    private boolean isUser(long id){
        return usersDao.findAllById(id) != null;
    }
    public Users loginByIdentifier(String identifier){
        return usersDao.findAllByIdentifier(identifier);
    }
    public JSONObject getResult(Users users){
        JSONObject object = new JSONObject();
        if (users != null){
            object.put("nickname",users.getNickname());
            object.put("sex", users.getSex());
            object.put("birthday", users.getBirthday());
            object.put("uid", users.getUid());
            object.put("token", users.getToken());
            object.put("phone", users.getPhone());
            object.put("avatar",users.getAvatar());
            object.put("gold",users.getGold());
            object.put("diamond", users.getDiamond());
            object.put("invite",users.getInvite());
            object.put("superior", users.getSuperior());
            object.put("expired",users.getExpireds());
            object.put("experience",users.getExperience());
            object.put("remommends",videoRecommendsDao.countAllByUid(users.getId()));
            object.put("follows", userFollowsDao.countAllByUid(users.getId()));
            object.put("fans", userFollowsDao.countAllByToUid(users.getId()));
        }
        return object;
    }
    public Users getUserByPhone(String phone){
        return usersDao.findAllByPhone(phone);
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
    public JSONObject getShareCount(Users user) {
        JSONObject object = new JSONObject();
//        object.put("count",usersDao.countAllBySuperior(user.getId()));
        object.put("count",shareRecordsDao.countAllByUid(user.getId()));
        object.put("bgImage", systemConfigService.getValueByKey("shareBgImage"));
        object.put("shareText", systemConfigService.getValueByKey("shareText"));
        return object;
    }

    public JSONObject getList(String d) {
        JSONObject data = JSONObject.parseObject(d);
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
            if (data.get("sort") != null){
                if (data.get("sort").toString().equals("+id")){
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
                }else {
                    pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
                }
            }else {
                pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            }
            if (data.get("title") == null && data.get("status") == null){
                usersPage = usersDao.findAll(pageable);
            }else if (StringUtils.isEmpty(data.get("title").toString()) && StringUtils.isEmpty(data.get("status").toString())){
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
        object.put("total",usersPage.getTotalPages());
        JSONArray array = new JSONArray();
        for (Users user: usersPage.getContent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",user.getId());
            jsonObject.put("nickname",user.getNickname());
            jsonObject.put("signature",user.getSignature());
            jsonObject.put("sex",user.getSex());
            jsonObject.put("birthday",user.getBirthday());
            jsonObject.put("ctime",user.getCtime());
            jsonObject.put("utime",user.getUtime());
            jsonObject.put("gold",user.getGold());
            jsonObject.put("diamond",user.getDiamond());
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
}
