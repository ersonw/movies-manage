package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoReport;
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
public interface VideoReportDao extends JpaRepository<VideoReport, Integer>, CrudRepository<VideoReport, Integer> {
    VideoReport findAllById(long id);
    VideoReport findAllByUidAndVid(long uid, long vid);
    Page<VideoReport> findAllByUid(long uid, Pageable pageable);
    Page<VideoReport> findAllByVid(long vid, Pageable pageable);
    Page<VideoReport> findAllByStatus(int status, Pageable pageable);
    @Modifying
    @Query(value = "DELETE FROM `video_report` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
