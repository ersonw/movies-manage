package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDao extends JpaRepository<Users, Integer>, CrudRepository<Users, Integer> {
    Users findAllById(long id);
    Users findAllByIdentifier(String id);
    Users findAllByUid(String uid);
    Users findAllByPhone(String phone);
    Users findAllByInvite(String invite);
    Page<Users> findAllByNicknameLikeAndStatus(String nickname,int status, Pageable pageable);
    long countAllBySuperior(long uid);
    Page<Users> findAllBySuperior(long uid, Pageable pageable);
    List<Users> findAllBySuperior(long uid);
    @Query(value = "select * from users where status =:status and (nickname like %:title% or phone like %:title% or uid like %:title% ) ", nativeQuery = true)
    Page<Users> findAllByNicknamelike(String title,int status , Pageable pageable);
    Page<Users> findAllByNicknameLikeOrPhoneLikeOrUidLike(String nickname, String phone,String uid, Pageable pageable);
    Page<Users> findAllByStatus(int status, Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
    Long countAllByTime();
    @Query(value = "SELECT COUNT(*) FROM users WHERE ctime > :time  AND status=1", nativeQuery = true)
    Long countAllByTime(long time);
    @Query(value = "SELECT COUNT(*) FROM users WHERE ctime > :t1 and ctime < :t2 AND status=1", nativeQuery = true)
    Long countAllByTime(long t1, long t2);
}
