package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCommentsDao extends JpaRepository<VideoComments, Integer>, CrudRepository<VideoComments, Integer> {
}
