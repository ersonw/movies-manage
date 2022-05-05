package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.DeductionOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeductionOrderDao extends JpaRepository<DeductionOrder, Long>, CrudRepository<DeductionOrder, Long> {
//    @Query(value = "SELECT SUM(diamond) FROM diamond_records WHERE uid=:uid AND status=1", nativeQuery = true)
//    long countAllByBalance(long uid);
    DeductionOrder findAllById(long id);
    Page<DeductionOrder> findAllByUid(long uid, Pageable pageable);
    List<DeductionOrder> findAllByUid(long uid);
    @Query(value = "SELECT SUM(amount) FROM deduction_order", nativeQuery = true)
    Long countAllByTime();
    @Query(value = "SELECT SUM(amount) FROM deduction_order WHERE add_time > :time", nativeQuery = true)
    Long countAllByTime(long time);
    @Query(value = "SELECT SUM(amount) FROM deduction_order WHERE add_time > :t1 and add_time < :t2", nativeQuery = true)
    Long countAllByTime(long t1, long t2);
}
