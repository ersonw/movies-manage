package com.telebott.moviesmanage.dao;


import com.telebott.moviesmanage.entity.CommodityGold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityGoldDao   extends JpaRepository<CommodityGold, Integer>, CrudRepository<CommodityGold, Integer> {
    List<CommodityGold> findAllByStatus(int status);
    CommodityGold findAllById(long id);
}
