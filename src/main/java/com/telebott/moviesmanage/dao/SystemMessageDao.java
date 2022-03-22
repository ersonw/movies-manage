package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.SystemMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMessageDao extends JpaRepository<SystemMessage, Integer>, CrudRepository<SystemMessage, Integer> {
}
