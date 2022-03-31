package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.ShowPayOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowPayOrdersDao extends JpaRepository<ShowPayOrders, Integer>, CrudRepository<ShowPayOrders, Integer> {
    ShowPayOrders findAllById(long id);
    ShowPayOrders findAllByOrderNo(String no);
}
