package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoShares;
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
public interface VideoSharesDao extends JpaRepository<VideoShares, Integer>, CrudRepository<VideoShares, Integer> {
    VideoShares findAllById(long id);
    VideoShares findAllByUidAndToUidAndVid(long uid, long tid, long vid);
    VideoShares findAllByToUidAndVid(long tid, long vid);
    Page<VideoShares> findAllByUid(long uid, Pageable pageable);
    Page<VideoShares> findAllByToUid(long tid, Pageable pageable);
    Page<VideoShares> findAllByVid(long vid, Pageable pageable);
    Page<VideoShares> findAllByUidAndToUidAndVid(long uid, long tid, long vid, Pageable pageable);
    @Modifying
    @Query(value = "DELETE FROM `video_shares` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
