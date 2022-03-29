package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoLikesDao extends JpaRepository<VideoLikes, Integer>, CrudRepository<VideoLikes, Integer> {
    VideoLikes findAllById(long id);
    Page<VideoLikes> findAllByUid(long uid, Pageable pageable);
    Page<VideoLikes> findAllByVid(long vid, Pageable pageable);
    VideoLikes findAllByUidAndVid(long uid,long vid);
    long countAllByVid(long vid);
    @Modifying
    @Query(value = "DELETE FROM `video_likes` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
