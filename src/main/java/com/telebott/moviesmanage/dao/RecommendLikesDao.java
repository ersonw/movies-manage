package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.RecommendLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RecommendLikesDao extends JpaRepository<RecommendLikes, Integer>, CrudRepository<RecommendLikes, Integer> {
    RecommendLikes findAllByUidAndRid(long uid, long rid);
    RecommendLikes findAllById(long id);
    long countAllByRid(long rid);
    @Modifying
    @Query(value = "DELETE FROM recommend_likes WHERE rid=:rid", nativeQuery = true)
    void deleteAllByRid(long rid);
}
