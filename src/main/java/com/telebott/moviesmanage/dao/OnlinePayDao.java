package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.OnlinePay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnlinePayDao   extends JpaRepository<OnlinePay, Integer>, CrudRepository<OnlinePay, Integer> {
    List<OnlinePay> findAllByStatus(int status);
    OnlinePay findAllById(long id);
}
