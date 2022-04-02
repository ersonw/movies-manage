package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.ShowPay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowPayDao extends JpaRepository<ShowPay, Long>, CrudRepository<ShowPay, Long> {
    ShowPay findAllById(long id);
    ShowPay findAllByMchId(String mid);

    ShowPay findAllByTitleOrMchId(String title, String mchId);

    ShowPay findAllByTitle(String title);

    Page<ShowPay> findAllByTitleLike(String title, Pageable pageable);
}
