package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoFeatureds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface VideoFeaturedsDao extends JpaRepository<VideoFeatureds, Integer>, CrudRepository<VideoFeatureds, Integer> {
    List<VideoFeatureds> findAllByStatus(int status);
    VideoFeatureds findAllById(long id);
    VideoFeatureds findAllByTitle(String title);
    Page<VideoFeatureds> findAllByIdOrTitleLike(Long title, String title1, Pageable pageable);
//    @Modifying
//    @Query(value = "DELETE FROM `video_featured_records` WHERE `vid`=:vid", nativeQuery = true)
//    void deleteAllByVid(long vid);
}
