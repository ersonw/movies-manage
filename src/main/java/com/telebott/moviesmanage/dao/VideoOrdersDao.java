package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.VideoOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface VideoOrdersDao extends JpaRepository<VideoOrders, Integer>, CrudRepository<VideoOrders, Integer> {
    VideoOrders findAllById(long id);
    VideoOrders findAllByUidAndVid(long uid, long vid);
    Page<VideoOrders> findAllByUid(long uid, Pageable pageable);
    Page<VideoOrders> findAllByVid(long vid, Pageable pageable);
    @Modifying
    @Query(value = "DELETE FROM `video_orders` WHERE `vid`=:vid", nativeQuery = true)
    void deleteAllByVid(long vid);
}
