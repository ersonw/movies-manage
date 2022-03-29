package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoFeaturedRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoFeaturedRecordsDao extends JpaRepository<VideoFeaturedRecords, Integer>, CrudRepository<VideoFeaturedRecords, Integer> {
    List<VideoFeaturedRecords> findAllByFid(long fid);
    Page<VideoFeaturedRecords> findAllByFid(long fid, Pageable pageable);
    @Query(value = "SELECT r.id,r.vid,r.fid,r.add_time,(IF(v.play > 0,v.play,(SELECT COUNT(*) FROM video_play AS vp WHERE vp.vid = v.id))) AS c FROM `video_featured_records` as r LEFT JOIN videos v on v.id = r.vid ORDER BY c DESC  LIMIT :page,:limit", nativeQuery = true)
    List<VideoFeaturedRecords> findHots(int page, int limit);
    @Query(value = "SELECT r.id,r.vid,r.fid,r.add_time,(IF(v.play > 0,v.play,(SELECT COUNT(*) FROM video_play AS vp WHERE vp.vid = v.id))) AS c FROM `video_featured_records` as r LEFT JOIN videos v on v.id = r.vid where r.fid=:id ORDER BY c DESC  LIMIT :page,:limit", nativeQuery = true)
    List<VideoFeaturedRecords> findHotsByTag(long id,int page, int limit);
    @Modifying
    @Query(value = "DELETE FROM `video_featured_records` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
