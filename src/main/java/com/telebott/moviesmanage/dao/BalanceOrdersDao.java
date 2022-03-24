package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.BalanceOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceOrdersDao extends JpaRepository<BalanceOrders, Integer>, CrudRepository<BalanceOrders, Integer> {
    BalanceOrders findAllById(long id);
    Page<BalanceOrders> findAllById(long id, Pageable pageable);
    Page<BalanceOrders> findAllByStatus(int status, Pageable pageable);
    Page<BalanceOrders> findAllByIdAndStatus(long id, int status, Pageable pageable);
    Page<BalanceOrders> findAllByUidAndStatus(long uid, int status, Pageable pageable);
    Page<BalanceOrders> findAllByUid(long uid,Pageable pageable);
    long countAllByUidAndStatus(long uid, int status);
    long countAllByUid(long uid);
    @Query(value = "SELECT SUM(amount) FROM balance_orders WHERE uid=:uid AND status=1", nativeQuery = true)
    long countAllByBalance(long uid);
}
