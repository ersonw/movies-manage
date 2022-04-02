package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.ShowPayOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowPayOrdersDao extends JpaRepository<ShowPayOrders, Long>, CrudRepository<ShowPayOrders, Long> {
    ShowPayOrders findAllById(long id);
    ShowPayOrders findAllByOrderNo(String no);

    Page<ShowPayOrders> findAllByOrderNoLike(String title, Pageable pageable);
}
