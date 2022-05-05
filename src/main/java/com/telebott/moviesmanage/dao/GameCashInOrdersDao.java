package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.GameCashInOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCashInOrdersDao  extends JpaRepository<GameCashInOrders, Long>, CrudRepository<GameCashInOrders, Long> {
    GameCashInOrders findAllById(long id);
    Page<GameCashInOrders> findAllByUid(long uid, Pageable pageable);
    GameCashInOrders findAllByOrderId(String orderId);
    long countAllByUid(long uid);
    long countAllByUidAndStatus(long uid, int status);
    @Query(value = "SELECT SUM(amount) FROM game_cash_in_orders WHERE status=1", nativeQuery = true)
    Long countAllByTime();
    @Query(value = "SELECT SUM(amount) FROM game_cash_in_orders WHERE add_time > :time  AND status=1", nativeQuery = true)
    Long countAllByTime(long time);
    @Query(value = "SELECT SUM(amount) FROM game_cash_in_orders WHERE add_time > :t1 and add_time < :t2 AND status=1", nativeQuery = true)
    Long countAllByTime(long t1, long t2);
}
