package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.GoldRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldRecordsDao   extends JpaRepository<GoldRecords, Integer>, CrudRepository<GoldRecords, Integer> {
    Page<GoldRecords> findAllByUid(long id, Pageable pageable);
}