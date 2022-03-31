package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoFavorites;
import com.telebott.moviesmanage.entity.VideoFeatureds;
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
public interface VideoFavoritesDao extends JpaRepository<VideoFavorites, Integer>, CrudRepository<VideoFavorites, Integer> {
    VideoFavorites findAllById(long id);
    VideoFavorites findAllByUidAndVid(long uid, long vid);
    Page<VideoFavorites> findAllByUid(long uid, Pageable pageable);
    Page<VideoFavorites> findAllByVid(long vid, Pageable pageable);
    long countAllByVid(long vid);
    @Modifying
    @Query(value = "DELETE FROM `video_favorites` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);

}
