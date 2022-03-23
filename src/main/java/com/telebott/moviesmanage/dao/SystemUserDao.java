package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserDao extends JpaRepository<SystemUser, Integer>, CrudRepository<SystemUser, Integer> {
    SystemUser findAllById(long id);
    SystemUser findAllByUsernameAndPasswordAndStatus(String username,String password, int status);
    SystemUser findAllByUsernameAndPassword(String username,String password);
    SystemUser findAllByUsernameAndStatus(String username, int status);
    SystemUser findAllByUsername(String username);
}
