package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.CommodityDiamond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommodityDiamondDao  extends JpaRepository<CommodityDiamond, Integer>, CrudRepository<CommodityDiamond, Integer> {
    List<CommodityDiamond> findAllByStatus(int status);
    CommodityDiamond findAllById(long id);

    Page<CommodityDiamond> findAllById(Long title, Pageable pageable);

    CommodityDiamond findAllByDiamond(long diamond);
}
