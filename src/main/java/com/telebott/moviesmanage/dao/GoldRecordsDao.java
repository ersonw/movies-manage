package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.GoldRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldRecordsDao   extends JpaRepository<GoldRecords, Integer>, CrudRepository<GoldRecords, Integer> {
    Page<GoldRecords> findAllByUid(long id, Pageable pageable);
    GoldRecords findAllById(long id);
    @Query(value = "SELECT SUM(gold) FROM gold_records WHERE uid=:uid AND status=1", nativeQuery = true)
    long countAllByBalance(long uid);
    long countAllByUid(long uid);
    Page<GoldRecords> findAllByStatus(int status, Pageable pageable);
    Page<GoldRecords> findAllById(long id, Pageable pageable);
    Page<GoldRecords> findAllByUidAndStatus(long id, int status, Pageable pageable);
    Page<GoldRecords> findAllByIdAndStatus(long id, int status, Pageable pageable);
    long countAllByUidAndStatus(long id, int i);
}
