package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.OnlineOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineOrderDao extends JpaRepository<OnlineOrder, Long>, CrudRepository<OnlineOrder, Long> {
    Page<OnlineOrder> findAllByUid(long uid, Pageable pageable);
    long countAllByOrderNo(String oid);
    OnlineOrder findAllByOrderId(String oid);
    long countAllByOrderNoAndStatus(String oid, int status);
    OnlineOrder findAllById(long id);
    Page<OnlineOrder> findAllByOrderIdLikeOrOrderNoLike(String id, String no,Pageable pageable);

    OnlineOrder findAllByOrderNo(String orderId);
}
