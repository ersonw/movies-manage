package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.CommodityDiamond;
import com.telebott.moviesmanage.entity.CommodityVip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityVipDao  extends JpaRepository<CommodityVip, Integer>, CrudRepository<CommodityVip, Integer> {
    List<CommodityVip> findAllByStatus(int status);
    CommodityVip findAllById(long id);
    CommodityVip findAllByTitle(String title);
    Page<CommodityVip> findAllByTitleLike(String title, Pageable pageable);

}
