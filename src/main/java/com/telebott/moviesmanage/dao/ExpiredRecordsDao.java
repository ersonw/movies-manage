package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.ExpiredRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpiredRecordsDao extends JpaRepository<ExpiredRecords, Integer>, CrudRepository<ExpiredRecords, Integer> {
    ExpiredRecords findAllByUid(long uid);
}
