package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.MoblieConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoblieConfigDao extends JpaRepository<MoblieConfig, Integer>, CrudRepository<MoblieConfig, Integer> {
    MoblieConfig findAllById(long id);
    Page<MoblieConfig> findAll(Pageable pageable);
    Page<MoblieConfig> findAllByVersionLike(String version,Pageable pageable);
    List<MoblieConfig> findAll();

}
