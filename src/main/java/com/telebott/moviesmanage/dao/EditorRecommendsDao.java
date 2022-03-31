package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.EditorRecommends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface EditorRecommendsDao extends JpaRepository<EditorRecommends, Long>, CrudRepository<EditorRecommends, Long> {
    EditorRecommends findAllById(long id);
    @Query(value = "SELECT *,(face+funny+hot) AS c FROM editor_recommends WHERE `show_time`=:date and status = 1 ORDER BY c DESC", nativeQuery = true)
    List<EditorRecommends> findByDate(long date);
    @Query(value = "SELECT *,(face+funny+hot) AS c FROM editor_recommends WHERE `show_time`=:date ORDER BY c DESC", nativeQuery = true)
    Page<EditorRecommends> findByDate(long date, Pageable pageable);
    @Query(value = "SELECT * FROM editor_recommends WHERE `show_time`=:date  GROUP BY show_time ORDER BY show_time DESC", nativeQuery = true)
    Page<EditorRecommends> findAllByDate(long date, Pageable pageable);
    @Modifying
    @Query(value = "DELETE FROM `editor_recommends` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
    @Modifying
    @Query(value = "DELETE FROM `editor_recommends` WHERE show_time=:date", nativeQuery = true)
    void deleteAllByShowTime(long date);
    @Query(value = "SELECT * FROM editor_recommends  GROUP BY show_time ORDER BY show_time DESC", nativeQuery = true)
    Page<EditorRecommends> getAllByAll(Pageable pageable);

    EditorRecommends findAllByVidAndShowTime(long id, long showTime);
}
