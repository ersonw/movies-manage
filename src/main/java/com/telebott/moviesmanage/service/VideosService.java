package com.telebott.moviesmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.telebott.moviesmanage.dao.*;
import com.telebott.moviesmanage.entity.*;
import com.telebott.moviesmanage.util.TimeUtil;
import com.telebott.moviesmanage.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Transactional
@Service
public class VideosService {
    static int randomIndex = 0;
    @Autowired
    private UserService userService;
    @Autowired
    private VideosDao videosDao;
    @Autowired
    private VideoCommentsDao videoCommentsDao;
    @Autowired
    private VideoCollectsDao videoCollectsDao;
    @Autowired
    private VideoLikesDao videoLikesDao;
    @Autowired
    private VideoRecommendsDao videoRecommendsDao;
    @Autowired
    private VideoActorsDao videoActorsDao;
    @Autowired
    private VideoCategoryDao videoCategoryDao;
    @Autowired
    private SearchTagsDao searchTagsDao;
    @Autowired
    private VideoPlayDao videoPlayDao;
    @Autowired
    private VideoOrdersDao videoOrdersDao;
    @Autowired
    private VideoFavoritesDao videoFavoritesDao;
    @Autowired
    private DiamondRecordsDao diamondRecordsDao;
    @Autowired
    private RecommendLikesDao recommendLikesDao;
    @Autowired
    private ActorMeasurementsDao actorMeasurementsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserFollowsDao userFollowsDao;
    @Autowired
    private VideoReportDao videoReportDao;
    @Autowired
    private UserPostsDao userPostsDao;
    @Autowired
    private EditorRecommendsDao editorRecommendsDao;
    @Autowired
    private ShareRecordsDao shareRecordsDao;
    @Autowired
    private VideoSharesDao videoSharesDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private GoldRecordsDao goldRecordsDao;
    @Autowired
    private ExpiredRecordsDao expiredRecordsDao;
    @Autowired
    private VideoFeaturedsDao videoFeaturedsDao;
    @Autowired
    private VideoFeaturedRecordsDao videoFeaturedRecordsDao;

    public static int byteToUnsignedInt(byte data) {
        return data & 0xff;
    }
    public void handlerYzm(YzmData yzmData) {
        if (StringUtils.isEmpty(yzmData.getShareid())) return;
        Videos videos = videosDao.findAllByShareId(yzmData.getShareid());
        if (videos == null) {
            videos = new Videos();
            videos.setVodTimeAdd(System.currentTimeMillis());
            videos.setShareId(yzmData.getShareid());
        } else {
            videos.setVodTimeUpdate(System.currentTimeMillis());
        }
        if(videos.getPlay() == 0){
            long cardinality = 0;
            String cardinalityStr = systemConfigService.getValueByKey("cardinalityPlay");
            if (StringUtils.isNotEmpty(cardinalityStr)){
                cardinality = Long.parseLong(cardinalityStr);
            }
            cardinality = (long) (100+Math.random()*(cardinality-100+1));
            videos.setPlay(cardinality);
        }
        if(videos.getRecommends() == 0){
            long cardinality = 0;
            String cardinalityStr = systemConfigService.getValueByKey("cardinalityRecommend");
            if (StringUtils.isNotEmpty(cardinalityStr)){
                cardinality = Long.parseLong(cardinalityStr);
            }
            cardinality = (long) (100+Math.random()*(cardinality-100+1));
            videos.setRecommends(cardinality);
        }
        if (yzmData.getRpath().contains("/")){
            String[] rpath = yzmData.getRpath().split("/");
            for (int i = 0; i < rpath.length; i++) {
                rpath[i] = UrlUtil.encode(rpath[i]);
            }
            yzmData.setRpath(StringUtils.join(rpath,"/"));
        }else{
            yzmData.setRpath(UrlUtil.encode(yzmData.getRpath()));
        }
        if (yzmData.getPath().contains("/")){
            String[] path = yzmData.getPath().split("/");
            for (int i = 0; i < path.length; i++) {
                path[i] = UrlUtil.encode(path[i]);
            }
            yzmData.setPath(StringUtils.join(path,"/"));
        }else{
            yzmData.setPath(UrlUtil.encode(yzmData.getPath()));
        }
        String pic1 = yzmData.getRpath() + "/1.jpg";
        videos.setTitle(yzmData.getTitle());
        videos.setVodContent(videos.getTitle());
        videos.setStatus(1);
        if (StringUtils.isNotEmpty(yzmData.getCategory())) {
            VideoCategory category = videoCategoryDao.findAllByName(yzmData.getCategory());
            if (category != null) {
                videos.setVodClass(category.getId());
            }else {
                category = new VideoCategory();
                category.setAddTime(System.currentTimeMillis());
                category.setStatus(1);
                category.setName(yzmData.getCategory());
                videoCategoryDao.saveAndFlush(category);
                videos.setVodClass(category.getId());
            }
        }
        if (yzmData.getMetadata() != null) {
            videos.setVodDuration(yzmData.getMetadata().getTime());
        }
        if (!yzmData.getDomain().endsWith("/")) yzmData.setDomain(yzmData.getDomain()+"/");
        if (yzmData.getOutput() != null) {
            String picDomain = yzmData.getDomain();
            if (StringUtils.isNotEmpty(yzmData.getPicdomain())) picDomain = yzmData.getPicdomain();
//            assert pic1 != null;
            if (!picDomain.endsWith("/") && !pic1.startsWith("/")) {
                picDomain = picDomain + "/";
            }
            videos.setPicThumb(picDomain + pic1);
            if (StringUtils.isNotEmpty(yzmData.getOutput().getGif())) {
                String gif = yzmData.getOutput().getGif();
                videos.setGifThumb(yzmData.getDomain() + gif.replaceAll(yzmData.getOutdir(), ""));
            }
            if (yzmData.getOutput().getVideo() != null) {
                List<VideoData> videoDataList = yzmData.getOutput().getVideo();
                List<VideoPlayUrl> playUrls = new ArrayList<>();
                for (VideoData data : videoDataList) {
                    VideoPlayUrl playUrl = new VideoPlayUrl();
                    playUrl.setResolution(data.getResolution());
                    playUrl.setSize(data.getLength());
                    playUrl.setUrl(yzmData.getDomain() + yzmData.getRpath() + "/" + data.getBitrate() + "kb/hls/index.m3u8");
                    playUrls.add(playUrl);
                }
                videos.setVodPlayUrl(JSONArray.toJSONString(playUrls));
            }
        }
        String downloadDomain = yzmData.getDomain();
        if (StringUtils.isNotEmpty(yzmData.getMp4domain())) downloadDomain = yzmData.getMp4domain();
        videos.setVodDownUrl(downloadDomain + yzmData.getRpath() + "/mp4/" + yzmData.getPath() + ".mp4");
        videosDao.saveAndFlush(videos);
    }

    private JSONObject getVideoList(Videos video) {
        JSONObject item = new JSONObject();
        item.put("title", video.getTitle());
        item.put("id", video.getId());
        item.put("image", video.getPicThumb());
        item.put("number", video.getNumbers());
        item.put("diamond", video.getDiamond());
        if (video.getActor() > 0) {
            VideoActors videoActors = videoActorsDao.findAllById(video.getActor());
            if (videoActors != null) {
                item.put("actor", getActor(videoActors));
            }
        }
        if (video.getPlay() > 0) {
            item.put("play", video.getPlay());
        } else {
            item.put("play", videoPlayDao.countAllByVid(video.getId()));
        }
        if (video.getRecommends() > 0) {
            item.put("remommends", video.getRecommends());
        } else {
            item.put("remommends", videoRecommendsDao.countAllByVid(video.getId()));
        }
        return item;
    }

    private JSONArray getVideoList(List<Videos> videosList) {
        JSONArray array = new JSONArray();
        if (videosList.size() > 0 && videosList.get(0) == null) return array;
        for (int i = 0; i < videosList.size(); i++) {
            array.add(getVideoList(videosList.get(i)));
        }
        return array;
    }

    private JSONObject getVideoList(Page<Videos> videosPage) {
        JSONObject object = new JSONObject();
        object.put("list", getVideoList(videosPage.getContent()));
        object.put("total", videosPage.getTotalPages());
        return object;
    }



    private JSONObject getActor(VideoActors actors) {
        JSONObject object = new JSONObject();
        if (actors != null) {
            object.put("id", actors.getId());
            object.put("name", actors.getName());
            object.put("avatar", actors.getAvatar());
            object.put("collects", videoCollectsDao.countAllByAid(actors.getId()));
            object.put("work", videosDao.countAllByActor(actors.getId()));
            ActorMeasurements measurements = actorMeasurementsDao.findAllById(actors.getMeasurements());
            if (measurements != null) {
                object.put("measurements", measurements.getTitle());
            } else {
                object.put("measurements", "?罩杯");
            }
        }
        return object;
    }

    public JSONObject getRandom() {
        Pageable pageable = PageRequest.of(randomIndex, 20, Sort.by(Sort.Direction.DESC, "id"));
        Page<Videos> videosPage = videosDao.findAllByStatus(1, pageable);
        if (videosPage.getTotalPages() > (randomIndex + 1)) {
            randomIndex++;
        } else {
            randomIndex = 0;
        }
        return getVideoList(videosPage);
    }



    public JSONObject getList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<Videos> videosPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if ((data.get("title") == null || StringUtils.isEmpty(data.getString("title"))) && (data.get("class") == null || StringUtils.isEmpty(data.getString("class")))){
                videosPage = videosDao.findAll(pageable);
            }else if (data.get("class") == null || StringUtils.isEmpty(data.getString("class"))){
                title = "%"+data.getString("title")+"%";
                if (isNumberString(data.getString("title"))){
                    videosPage = videosDao.findAllByTitleLikeOrUid(title,data.getLong("title"),pageable);
                }else {
                    videosPage = videosDao.findAllByTitleLike(title,pageable);
                }
            }else if (data.get("title") == null || StringUtils.isEmpty(data.getString("title"))){
                long aid = data.getLong("class");
                VideoCategory category = videoCategoryDao.findAllById(aid);
                if (category != null){
                    videosPage = videosDao.findAllByVodClass(category.getId(),pageable);
                }else {
                    videosPage = videosDao.findAll(pageable);
                }
            }else {
                title = data.getString("title");
                long aid = data.getLong("class");
                VideoCategory category = videoCategoryDao.findAllById(aid);
                if (category == null){
                    videosPage = videosDao.findAllByTitleLikeOrUid("%"+title+"%",data.getLong("title"),pageable);
                }else {
//                    System.out.println("我执行了");
                    videosPage = videosDao.findAllByClass(title,category.getId(),pageable);
                }
            }
        }else {
            videosPage = videosDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        getVideoPage(object, array, videosPage);
        object.put("list",array);
//        object.put("total",videosPage.getTotalElements());
        return object;
    }

    public boolean delete(JSONObject data) {
        JSONArray array = JSONArray.parseArray(data.getString("list"));
        for (Object o: array) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(o));
            Videos video = videosDao.findAllById(object.getLong("id"));
            if (video != null){
                videoRecommendsDao.deleteAllByVid(video.getId());
                videoPlayDao.deleteAllByVid(video.getId());
                videoOrdersDao.deleteAllByVid(video.getId());
                videoFavoritesDao.deleteAllByVid(video.getId());
                videoSharesDao.deleteAllByVid(video.getId());
                videoReportDao.deleteAllByVid(video.getId());
                videoCommentsDao.deleteAllByVid(video.getId());
                videoFeaturedRecordsDao.deleteAllByVid(video.getId());
                videoLikesDao.deleteAllByVid(video.getId());
                editorRecommendsDao.deleteAllByVid(video.getId());
                videosDao.delete(video);
            }
        }
        return true;
    }

    public JSONObject getVodClass() {
        JSONObject object = new JSONObject();
        object.put("list", videoCategoryDao.findAll());
        return object;
    }

    public JSONObject getActor() {
        JSONObject object = new JSONObject();
        object.put("list", videoActorsDao.findAll());
        return object;
    }

    public JSONObject getActorVideos(int page, long id) {
        JSONObject object = new JSONObject();
        int limit = 20;
        page--;
        if (page<0) page =0;
        JSONArray array = new JSONArray();
        if (id > 0) {
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
            VideoActors actor = videoActorsDao.findAllById(id);
            if (actor != null){
                Page<Videos> videosPage = videosDao.findAllByActor(actor.getId(), pageable);
                getVideoPage(object, array, videosPage);
            }
        }
        object.put("list",array);
        return object;
    }

    public static Pageable getPageable(JSONObject data, int page, int limit, Pageable pageable) {
        if (data.get("sort") != null){
            if (data.getString("sort").equals("+id")){
                pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            }else {
                pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
            }
        }
        return pageable;
    }

    private void getVideoPage(JSONObject object, JSONArray array, Page<Videos> videosPage) {
        for ( Videos video : videosPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(video));
            if (video.getPlay() == 0){
                jsonObject.put("play", videoPlayDao.countAllByVid(video.getId()));
            }
            if (video.getRecommends() == 0){
                jsonObject.put("recommends",videoRecommendsDao.countAllByVid(video.getId()));
            }
            JSONObject userObject = new JSONObject();
            userObject.put("id",null);
            userObject.put("nickname", null);
            Users user = usersDao.findAllById(video.getUid());
            if (user != null){
                userObject.put("id",user.getId());
                userObject.put("nickname", user.getNickname());
            }
            jsonObject.put("user",userObject);
            jsonObject.put("collects", videoFavoritesDao.countAllByVid(video.getId()));
            array.add(jsonObject);
        }
        object.put("total", videosPage.getTotalElements());
    }
    private Videos _changeVideo(JSONObject data){
        if (data.get("id") == null) return null;
        Videos videos = videosDao.findAllById(Long.parseLong(data.getString("id")));
        if (videos != null){
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(videos));
            for (Map.Entry<String, Object> entry: data.entrySet()) {
                if (entry.getValue() != null){
                    object.put(entry.getKey(), entry.getValue());
                }
            }
            return JSONObject.toJavaObject(object, Videos.class);
        }
        return null;
    }
    public boolean update(JSONObject data) {
        JSONArray array = JSONArray.parseArray(data.getString("list"));
        for (Object o: array) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(o));
            object.put("vodTimeUpdate", System.currentTimeMillis());
            object.put("id",data.get("id"));
            object.put("actor",data.get("actor"));
            object.put("vodClass",data.get("vodClass"));
            object.put("status",data.get("status"));
            object.put("title",data.get("title"));
            object.put("diamond",data.get("diamond"));
            object.put("play",data.get("play"));
            object.put("recommends",data.get("recommends"));
            Videos video = _changeVideo(object);
            if (video != null){
                videosDao.saveAndFlush(video);
//                return true;
            }
        }
        return true;
    }

    public JSONObject getClassList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<VideoCategory> categoryPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty(data.getString("title"))){
                title = "%"+data.getString("title")+"%";
                categoryPage = videoCategoryDao.findAllByNameLike(title, pageable);
            }else {
                categoryPage = videoCategoryDao.findAll(pageable);
            }
        }else {
            categoryPage = videoCategoryDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for (VideoCategory category: categoryPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(category));
            jsonObject.put("count", videosDao.countAllByVodClass(category.getId()));
            array.add(jsonObject);
        }
        object.put("list",array);
        object.put("total",categoryPage.getTotalElements());
        return object;
    }

    public boolean addClass(JSONObject data) {
        int status = 0;
        if (data != null && data.get("name") != null && StringUtils.isNotEmpty(data.getString("name"))){
            if (data.get("status") != null && StringUtils.isNotEmpty(data.getString("status"))) status = data.getInteger("status");
            VideoCategory category = new VideoCategory();
            category.setStatus(status);
            category.setName(data.getString("name"));
            category.setAddTime(System.currentTimeMillis());
            VideoCategory videoCategory = videoCategoryDao.findAllByName(category.getName());
            if (videoCategory == null){
                videoCategoryDao.saveAndFlush(category);
                return true;
            }
        }
        return false;
    }

    public boolean updateClass(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoCategory category = videoCategoryDao.findAllById(data.getLong("id"));
            if (category != null){
                if (data.get("status") != null && StringUtils.isNotEmpty(data.get("status").toString())) category.setStatus(data.getInteger("status"));
                if (data.get("name") != null && StringUtils.isNotEmpty(data.get("name").toString())) category.setName(data.getString("name"));
                category.setAddTime(System.currentTimeMillis());
                videoCategoryDao.saveAndFlush(category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteClass(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoCategory category = videoCategoryDao.findAllById(data.getLong("id"));
            if (category != null){
                videoCategoryDao.delete(category);
                return true;
            }
        }
        return false;
    }

    public JSONObject getBoutiqueList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<VideoFeatureds> featuredsPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty(data.getString("title"))){
                title = "%"+data.getString("title")+"%";
                featuredsPage = videoFeaturedsDao.findAllByIdOrTitleLike(data.getLong("title"), title, pageable);
            }else {
                featuredsPage = videoFeaturedsDao.findAll(pageable);
            }
        }else {
            featuredsPage = videoFeaturedsDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for (VideoFeatureds feature : featuredsPage.getContent()) {
            List<VideoFeaturedRecords> records = videoFeaturedRecordsDao.findAllByFid(feature.getId());
            JSONArray jsonArray = new JSONArray();
            if (records.size() > 0){
                for (VideoFeaturedRecords record: records) {
                    Videos video = videosDao.findAllById(record.getVid());
                    if (video != null){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id",record.getId());
                        jsonObject.put("vid",video.getId());
                        jsonObject.put("title",video.getTitle());
                        jsonObject.put("picThumb",video.getPicThumb());
                        jsonArray.add(jsonObject);
                    }
                }
            }
            JSONObject d = new JSONObject();
            d.put("id", feature.getId());
            d.put("title", feature.getTitle());
            d.put("status", feature.getStatus());
            d.put("addTime", feature.getAddTime());
            d.put("records", jsonArray);
            array.add(d);
        }
        object.put("list",array);
        object.put("total",featuredsPage.getTotalElements());
        return object;
    }

    public boolean addBoutique(JSONObject data) {
        int status = 0;
        if (data != null && data.get("title") != null){
            if (data.get("status") != null && StringUtils.isNotEmpty(data.getString("status"))) status = data.getInteger("status");
            VideoFeatureds feature = videoFeaturedsDao.findAllByTitle(data.getString("title"));
            if (feature == null){
                feature = new VideoFeatureds();
                feature.setAddTime(System.currentTimeMillis());
                feature.setStatus(status);
                feature.setTitle(data.getString("title"));
                videoFeaturedsDao.saveAndFlush(feature);
                if (data.get("records") != null){
                    JSONArray array = JSONArray.parseArray(data.getString("records"));
                    for (Object object: array) {
                        JSONObject o = JSONObject.parseObject(JSONObject.toJSONString(object));
                        if (StringUtils.isNotEmpty(o.getString("vid"))){
                            Videos video = videosDao.findAllById(o.getLong("vid"));
                            if (video != null){
                                VideoFeaturedRecords record = new VideoFeaturedRecords();
                                record.setAddTime(System.currentTimeMillis());
                                record.setVid(video.getId());
                                record.setFid(feature.getId());
                                videoFeaturedRecordsDao.saveAndFlush(record);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateBoutique(JSONObject data) {
        if (data != null){
            if (StringUtils.isNotEmpty(data.getString("id"))){
                VideoFeatureds feature = videoFeaturedsDao.findAllById(data.getLong("id"));
                if (feature != null){
                    feature.setAddTime(System.currentTimeMillis());
                    if (StringUtils.isNotEmpty(data.getString("title"))) feature.setTitle(data.getString("title"));
                    if (StringUtils.isNotEmpty(data.getString("status"))) feature.setStatus(data.getInteger("status"));
                    videoFeaturedsDao.saveAndFlush(feature);
                    if (StringUtils.isNotEmpty(data.getString("records"))){
                        JSONArray array = JSONArray.parseArray(data.getString("records"));
                        for (Object o: array) {
                            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(o));
                            if (object.get("vid") != null){
                                Videos video = videosDao.findAllById(object.getLong("vid"));
                                if (video != null){
                                    if (object.get("id") != null){
                                        VideoFeaturedRecords record = videoFeaturedRecordsDao.findAllById(object.getLong("id"));
                                        if (record == null){
                                            record = new VideoFeaturedRecords();
                                            record.setFid(feature.getId());
                                            record.setVid(video.getId());
                                            record.setAddTime(System.currentTimeMillis());
                                            videoFeaturedRecordsDao.saveAndFlush(record);
                                        }
                                    }else {
                                        VideoFeaturedRecords record = videoFeaturedRecordsDao.findAllByFidAndVid(feature.getId(),video.getId());
                                        if (record == null){
                                            record = new VideoFeaturedRecords();
                                            record.setFid(feature.getId());
                                            record.setVid(video.getId());
                                            record.setAddTime(System.currentTimeMillis());
                                            videoFeaturedRecordsDao.saveAndFlush(record);
                                        }

                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteRecord(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoFeaturedRecords records = videoFeaturedRecordsDao.findAllById(data.getLong("id"));
            if (records != null){
                videoFeaturedRecordsDao.delete(records);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBoutique(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoFeatureds feature = videoFeaturedsDao.findAllById(data.getLong("id"));
            if (feature != null){
                videoFeaturedRecordsDao.deleteAllByFid(data.getLong("id"));
                videoFeaturedsDao.delete(feature);
                return true;
            }
        }
        return false;
    }
    public JSONObject getActorVideoList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        JSONArray array = new JSONArray();
        if (data != null && data.get("id") != null) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            VideoActors actor = videoActorsDao.findAllById(data.getLong("id"));
            if (actor != null){
                Page<Videos> videosPage = videosDao.findAllByActor(actor.getId(),pageable);
                object.put("total",videosPage.getTotalElements());
                for (Videos video: videosPage.getContent()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",video.getId());
                    jsonObject.put("title",video.getTitle());
                    jsonObject.put("picThumb",video.getPicThumb());
                    array.add(jsonObject);
                }
            }
        }
        object.put("list",array);
        return object;
    }
    public JSONObject getRecommendVideoList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        JSONArray array = new JSONArray();
        if (data != null && data.get("id") != null) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            Videos video = videosDao.findAllById(data.getLong("id"));
            if (video != null){
                Page<VideoRecommends> recommendsPage = videoRecommendsDao.findAllByVid(video.getId(),pageable);
                object.put("total",recommendsPage.getTotalElements());
                for (VideoRecommends recommend :recommendsPage.getContent()) {
                    Users user = usersDao.findAllById(recommend.getUid());
                    if (user != null){
                        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(recommend));
                        jsonObject.put("user", user);
                        jsonObject.put("likes", recommendLikesDao.countAllByRid(recommend.getId()));
                        array.add(jsonObject);
                    }
                }
            }
        }
        object.put("list",array);
        return object;
    }
    public JSONObject getActorList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<VideoActors> actorsPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty(data.getString("title"))){
                title = "%"+data.getString("title")+"%";
                actorsPage = videoActorsDao.findAllByNameLike(title, pageable);
            }else {
                actorsPage = videoActorsDao.findAll(pageable);
            }
        }else {
            actorsPage = videoActorsDao.findAll(pageable);
        }
        JSONArray array = new JSONArray();
        for (VideoActors actor : actorsPage.getContent()) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(actor));
            JSONObject actorVideos = new JSONObject();
            actorVideos.put("id",actor.getId());
            actorVideos = getActorVideoList(actorVideos);
            jsonObject.put("videos",actorVideos);
            jsonObject.put("collects",videoCollectsDao.countAllByAid(actor.getId()));
            array.add(jsonObject);
        }
        object.put("list",array);
        object.put("total",actorsPage.getTotalElements());
        return object;
    }

    public JSONObject getMeasurements(JSONObject data) {
        List<ActorMeasurements> measurements = actorMeasurementsDao.findAll();
        JSONObject object = new JSONObject();
        object.put("total",measurements.size());
        object.put("list", measurements);
        return object;
    }

    public boolean removeActorVideo(JSONObject data) {
        if (data != null && data.get("id") != null){
            Videos video = videosDao.findAllById(data.getLong("id"));
            if (video != null){
                video.setActor(0);
                videosDao.saveAndFlush(video);
                return true;
            }
        }
        return false;
    }

    public boolean addActor(JSONObject data) {
        if (data != null && data.get("name") != null && data.get("avatar") != null){
            int status = 0;
            long mid = 0;
            if (data.get("status") != null) status = (data.getInteger("status"));
            if (data.get("measurements") != null) {
                ActorMeasurements measurement = actorMeasurementsDao.findAllById(data.getLong("measurements"));
                if (measurement != null) mid = measurement.getId();
            }
            VideoActors actor = videoActorsDao.findAllByName(data.getString("name"));
            if (actor == null){
                actor = new VideoActors();
                actor.setAddTime(System.currentTimeMillis());
                actor.setUpdateTime(actor.getAddTime());
                actor.setName(data.getString("name"));
                actor.setStatus(status);
                actor.setAvatar(data.getString("avatar"));
                actor.setMeasurements(mid);
                videoActorsDao.saveAndFlush(actor);
                return true;
            }
        }
        return false;
    }

    public boolean deleteActor(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoActors actor = videoActorsDao.findAllById(data.getLong("id"));
            if (actor != null){
                videosDao.removeAllByAid(actor.getId());
                videoCollectsDao.deleteAllByAid(actor.getId());
                videoActorsDao.delete(actor);
                return true;
            }
        }
        return false;
    }

    public boolean updateActor(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoActors actor = videoActorsDao.findAllById(data.getLong("id"));
            if (actor != null){
                if(data.get("status") != null) actor.setStatus(data.getInteger("status"));
                if(data.get("avatar") != null) actor.setAvatar(data.getString("avatar"));
                if(data.get("name") != null) actor.setName(data.getString("name"));
                if(data.get("measurements") != null) {
                    ActorMeasurements measurement = actorMeasurementsDao.findAllById(data.getLong("measurements"));
                    if (measurement != null){
                        actor.setMeasurements(measurement.getId());
                    }
                }
                videoActorsDao.saveAndFlush(actor);
                if (StringUtils.isNotEmpty(data.getString("videos"))){
                    JSONObject videos = JSONObject.parseObject(data.getString("videos"));
                    if (videos != null && videos.get("list") != null && StringUtils.isNotEmpty(videos.getString("list"))){
                        JSONArray array = JSONArray.parseArray(videos.getString("list"));
                        for (Object o: array) {
                            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(o));
                            if (object.get("id") != null){
                                Videos video = videosDao.findAllById(object.getLong("id"));
                                if (video != null && video.getActor() != actor.getId()){
                                    video.setActor(actor.getId());
                                    videosDao.saveAndFlush(video);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public JSONObject getUnActorVideos(JSONObject data) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        if (data != null) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            Page<Videos> videosPage ;
            if (data.get("title") == null || StringUtils.isEmpty(data.getString("title"))){
                videosPage = videosDao.findAllByActor(0, pageable);
            }else {
                videosPage = videosDao.findAllByActorAndTitleLike(0,"%"+data.getString("title")+"%", pageable);
            }
            getVideoPage(object,array,videosPage);
        }
        object.put("list",array);
        return object;
    }

    public JSONObject getWolfFriendList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<VideoRecommends> recommendsPage;
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
        }
        recommendsPage = videoRecommendsDao.getByAll(pageable);
        JSONArray array = new JSONArray();
        for (VideoRecommends recommend : recommendsPage.getContent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",recommend.getId());
            jsonObject.put("status",recommend.getStatus());
            jsonObject.put("addTime",recommend.getAddTime());
            Videos video = videosDao.findAllById(recommend.getVid());
            if (video != null){
                jsonObject.put("title",video.getTitle());
                jsonObject.put("picThumb",video.getPicThumb());
                JSONObject recommends = new JSONObject();
                recommends.put("id",recommend.getVid());
                recommends = getRecommendVideoList(recommends);
                jsonObject.put("recommends",recommends);
                array.add(jsonObject);
            }
        }
        object.put("list",array);
        object.put("total",recommendsPage.getTotalElements());
        return object;
    }

    public boolean removeWolfUser(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoRecommends recommend = videoRecommendsDao.findAllById(data.getLong("id"));
            if (recommend != null){
                recommendLikesDao.deleteAllByRid(recommend.getId());
                videoRecommendsDao.delete(recommend);
                return true;
            }
        }
        return false;
    }

    public boolean deleteWolf(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoRecommends recommend = videoRecommendsDao.findAllById(data.getLong("id"));
            if (recommend != null){
                videoRecommendsDao.deleteAllByVid(recommend.getVid());
                return true;
            }
        }
        return false;
    }
    //判断字符串是否为数字
    public  static boolean isNumberString(String s){
        for (int i=0;i< s.length(); i++){
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }
    public JSONObject getVideoOrdersList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        String title = null;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<VideoOrders> ordersPage;
        JSONArray array = new JSONArray();
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (data.get("title") != null && StringUtils.isNotEmpty(data.getString("title")) && isNumberString(data.getString("title"))){
                Videos video = videosDao.findAllById(data.getLong("title"));
                if (video != null){
                    ordersPage = videoOrdersDao.getAllByAll(video.getId(),pageable);
                }else {
                    ordersPage = videoOrdersDao.getAllByAll(pageable);
                }
            }else {
                ordersPage = videoOrdersDao.getAllByAll(pageable);
            }
        }else {
            ordersPage = videoOrdersDao.getAllByAll(pageable);
        }
        for (VideoOrders order : ordersPage.getContent()) {
            Videos video = videosDao.findAllById(order.getVid());
            if (video != null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",video.getId());
                jsonObject.put("title",video.getTitle());
                jsonObject.put("picThumb",video.getPicThumb());
                JSONObject orders = new JSONObject();
                orders.put("id",video.getId());
                jsonObject.put("orders",getOrderList(orders));
                array.add(jsonObject);
            }
        }
        object.put("list",array);
        object.put("total",ordersPage.getTotalElements());
        return object;
    }
    public JSONObject getOrderList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        JSONArray array = new JSONArray();
        if (data != null && data.get("id") != null) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            Videos video = videosDao.findAllById(data.getLong("id"));
            if (video != null){
                Page<VideoOrders> ordersPage = videoOrdersDao.findAllByVid(video.getId(),pageable);
                object.put("total",ordersPage.getTotalElements());
                for (VideoOrders order: ordersPage.getContent()) {
                    Users user = usersDao.findAllById(order.getUid());
                    if (user != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id",order.getId());
                        jsonObject.put("status",order.getStatus());
                        jsonObject.put("addTime",order.getAddTime());
                        jsonObject.put("user",user.getNickname());
                        array.add(jsonObject);
                    }
                }
            }
        }
        object.put("list",array);
        return object;
    }

    public boolean removeOrderUser(JSONObject data) {
        if (data != null && data.get("id") != null){
            VideoOrders orders = videoOrdersDao.findAllById(data.getLong("id"));
            if (orders != null){
                videoOrdersDao.delete(orders);
                return true;
            }
        }
        return false;
    }

    public boolean deleteOrderVideo(JSONObject data) {
        if (data != null && data.get("id") != null){
            Videos video = videosDao.findAllById(data.getLong("id"));
            if (video != null){
                videoOrdersDao.deleteAllByVid(video.getId());
                return true;
            }
        }
        return false;
    }
    public JSONObject getEditorRecommendVideoList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        page--;
        if (page<0) page =0;
        JSONArray array = new JSONArray();
        if (data != null && data.get("date") != null) {
            long date = 0;
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            page--;
            if (page<0) page =0;
            Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (StringUtils.isNotEmpty(data.getString("date")) && isNumberString(data.getString("date"))) date = TimeUtil.dayToTimeOnly(data.getString("date"));
            if (date > 0){
                Page<EditorRecommends> recommendsPage = editorRecommendsDao.findByDate(date,pageable);
                for (EditorRecommends recommend: recommendsPage.getContent()) {
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(recommend));
                    Videos video = videosDao.findAllById(recommend.getVid());
                    if (video != null){
                        if (StringUtils.isEmpty(recommend.getTitle())){
                            jsonObject.put("title",video.getTitle());
                        }
                        jsonObject.put("picThumb",video.getPicThumb());
                    }
                    array.add(jsonObject);
                }
                object.put("total",recommendsPage.getTotalElements());
            }
        }
        object.put("list",array);
        return object;
    }
    public JSONObject getEditorRecommendList(JSONObject data) {
        JSONObject object = new JSONObject();
        int page = 1;
        int limit = 20;
        long date = 0;
        page--;
        if (page<0) page =0;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<EditorRecommends> recommendsPage;
        JSONArray array = new JSONArray();
        if (data != null ) {
            if (data.get("page") != null){
                page = Integer.parseInt(data.get("page").toString());
            }
            if (data.get("date") != null && isNumberString(data.getString("date"))) date = TimeUtil.dayToTimeOnly(data.getString("date"));
            page--;
            if (page<0) page =0;
            pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "id"));
            pageable = getPageable(data, page, limit, pageable);
            if (date > 0){
                recommendsPage = editorRecommendsDao.findAllByDate(date,pageable);
            }else {
                recommendsPage = editorRecommendsDao.getAllByAll(pageable);
            }
        }else {
            recommendsPage = editorRecommendsDao.getAllByAll(pageable);
        }
        for (EditorRecommends recommend : recommendsPage.getContent()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", recommend.getShowTime());
            jsonObject.put("videos", getEditorRecommendVideoList(jsonObject));
            array.add(jsonObject);
        }
        object.put("list",array);
        object.put("total",recommendsPage.getTotalElements());
        return object;
    }

    public boolean addEditorRecommend(JSONObject data) {
        EditorRecommends recommend = JSONObject.toJavaObject(data,EditorRecommends.class);
        if (recommend != null && recommend.getVid() > 0 && recommend.getShowTime() > 0){
            Videos video = videosDao.findAllById(recommend.getVid());
            if (video != null){
                if(video.getTitle().equals(data.getString("title"))){
                    recommend.setTitle(null);
                }
                EditorRecommends editorRecommends = editorRecommendsDao.findAllByVidAndShowTime(video.getId(),recommend.getShowTime());
                if (editorRecommends == null) {
                    recommend.setAddTime(System.currentTimeMillis());
                    editorRecommendsDao.saveAndFlush(recommend);
                    return true;
                }
            }
        }
        return false;
    }
    private EditorRecommends _changeEditorRecommend(JSONObject data){
        EditorRecommends recommend = editorRecommendsDao.findAllById(Long.parseLong(data.getString("id")));
        if (recommend != null){
            Videos video = videosDao.findAllById(recommend.getVid());
            if (video != null){
                if (data.getString("title").equals(video.getTitle())){
                    data.put("title",null);
                }
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(recommend));
                for (Map.Entry<String, Object> entry: data.entrySet()) {
                    if (entry.getValue() != null){
                        object.put(entry.getKey(), entry.getValue());
                    }
                }
                return JSONObject.toJavaObject(object, EditorRecommends.class);
            }
        }
        return null;
    }
    public boolean updateEditorRecommend(JSONObject data) {
        if (data != null && data.get("id") != null){
            data.put("addTime", System.currentTimeMillis());
            EditorRecommends recommend = _changeEditorRecommend(data);
            if (recommend != null){
                editorRecommendsDao.saveAndFlush(recommend);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEditorRecommend(JSONObject data) {
        if (data != null && data.get("date") != null && isNumberString(data.getString("date"))){
            editorRecommendsDao.deleteAllByShowTime(data.getLong("date"));
            return true;
        }
        return false;
    }

    public boolean removeEditorRecommend(JSONObject data) {
        if (data != null && data.get("id") != null){
            EditorRecommends recommend = editorRecommendsDao.findAllById(data.getLong("id"));
            if (recommend != null){
                editorRecommendsDao.delete(recommend);
                return true;
            }
        }
        return false;
    }

    public boolean clean() {
        return false;
    }

    public boolean cleanClass() {
        return false;
    }

    public boolean cleanBoutique() {
        return false;
    }
}
