package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoCollects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface VideoCollectsDao extends JpaRepository<VideoCollects, Integer>, CrudRepository<VideoCollects, Integer> {
    long countAllByAid(long aid);
    Page<VideoCollects> findAllByAid(long aid, Pageable pageable);
    VideoCollects findAllById(long id);
    VideoCollects findAllByUidAndAid(long uid, long aid);
    Page<VideoCollects> findAllByUid(long uid, Pageable pageable);
    @Modifying
    @Query(value = "DELETE FROM `video_collects` WHERE aid=:aid", nativeQuery = true)
    void deleteAllByAid(long aid);
}
