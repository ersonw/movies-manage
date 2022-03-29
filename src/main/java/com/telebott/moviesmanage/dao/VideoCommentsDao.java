package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCommentsDao extends JpaRepository<VideoComments, Integer>, CrudRepository<VideoComments, Integer> {
    VideoComments findAllById(long id);
    @Modifying
    @Query(value = "DELETE FROM `video_comments` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
