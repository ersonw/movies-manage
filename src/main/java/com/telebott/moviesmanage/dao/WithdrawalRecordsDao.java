package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.WithdrawalRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRecordsDao extends JpaRepository<WithdrawalRecords, Integer>, CrudRepository<WithdrawalRecords, Integer> {
    WithdrawalRecords findAllById(long id);
    Page<WithdrawalRecords> findAllByUid(long uid, Pageable pageable);
    WithdrawalRecords findAllByIdAndUid(long id, long uid);

    Page<WithdrawalRecords> findAllByorderNoLike(String title, Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM withdrawal_records WHERE status=0", nativeQuery = true)
    Long countAllBySQ();
    @Query(value = "SELECT SUM(amount) FROM withdrawal_records WHERE status=1", nativeQuery = true)
    Long countAllByTime();
    @Query(value = "SELECT SUM(amount) FROM withdrawal_records WHERE add_time > :time  AND status=1", nativeQuery = true)
    Long countAllByTime(long time);
    @Query(value = "SELECT SUM(amount) FROM withdrawal_records WHERE add_time > :t1 and add_time < :t2 AND status=1", nativeQuery = true)
    Long countAllByTime(long t1, long t2);
}
