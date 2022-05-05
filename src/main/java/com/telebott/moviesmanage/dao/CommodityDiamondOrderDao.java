package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.CommodityDiamondOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDiamondOrderDao   extends JpaRepository<CommodityDiamondOrder, Integer>, CrudRepository<CommodityDiamondOrder, Integer> {
    List<CommodityDiamondOrder> findAllByUidAndStatus(long uid, int status);
    CommodityDiamondOrder findAllByOrderId(String orderId);
    Page<CommodityDiamondOrder> findAllByUid(long uid, Pageable pageable);
    CommodityDiamondOrder findAllByIdAndUid(long id, long uid);
    @Query(value = "SELECT SUM(amount) FROM commodity_diamond_order WHERE status=1", nativeQuery = true)
    Long countAllByTime();
    @Query(value = "SELECT SUM(amount) FROM commodity_diamond_order WHERE ctime > :time  AND status=1", nativeQuery = true)
    Long countAllByTime(long time);
    @Query(value = "SELECT SUM(amount) FROM commodity_diamond_order WHERE ctime > :t1 and ctime < :t2 AND status=1", nativeQuery = true)
    Long countAllByTime(long t1, long t2);
}
