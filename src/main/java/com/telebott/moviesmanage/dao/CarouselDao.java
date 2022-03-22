package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselDao extends JpaRepository<Carousel, Integer>, CrudRepository<Carousel, Integer> {
    List<Carousel> findAllByStatus(int status);
}
