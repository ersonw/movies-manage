package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.SystemConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemConfigDao extends JpaRepository<SystemConfig, Integer>, CrudRepository<SystemConfig, Integer> {
    SystemConfig findAllById(long id);
    SystemConfig findAllByName(String name);
    List<SystemConfig> findAllByNameAndDes(String name,String des);
    Page<SystemConfig> findAllByNameLikeOrDesLike(String name, String des, Pageable pageable);
}
