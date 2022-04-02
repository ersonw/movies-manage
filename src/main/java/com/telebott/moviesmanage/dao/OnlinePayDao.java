package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.OnlinePay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnlinePayDao   extends JpaRepository<OnlinePay, Long>, CrudRepository<OnlinePay, Long> {
    List<OnlinePay> findAllByStatus(int status);
    OnlinePay findAllById(long id);

    OnlinePay findAllByTitle(String title);

    Page<OnlinePay> findAllByTitleLike(String title, Pageable pageable);
}
