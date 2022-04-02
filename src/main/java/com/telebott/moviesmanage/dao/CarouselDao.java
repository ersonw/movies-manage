package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.Carousel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselDao extends JpaRepository<Carousel, Long>, CrudRepository<Carousel, Long> {
    List<Carousel> findAllByStatus(int status);

    Page<Carousel> findAllByNameLike(String title, Pageable pageable);
    Carousel findAllById(long id);
    Carousel findAllByName(String name);
}
