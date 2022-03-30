package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoCategoryDao extends JpaRepository<VideoCategory, Integer>, CrudRepository<VideoCategory, Integer> {
    VideoCategory findAllById(long id);
    VideoCategory findAllByName(String name);
    List<VideoCategory> findAllByStatus(int status);
    Page<VideoCategory> findAllByIdOrNameLike(long id, String name, Pageable pageable);

    Page<VideoCategory> findAllByNameLike(String title, Pageable pageable);
}
