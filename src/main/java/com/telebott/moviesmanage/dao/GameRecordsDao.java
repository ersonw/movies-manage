package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.GameRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordsDao  extends JpaRepository<GameRecords, Long>, CrudRepository<GameRecords, Long> {
    GameRecords findAllById(long id);
    Page<GameRecords> findAllByUid(long uid, Pageable pageable);
    GameRecords findAllByRecordId(String recordId);
}
