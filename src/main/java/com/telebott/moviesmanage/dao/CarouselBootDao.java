package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.CarouselBoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselBootDao extends JpaRepository<CarouselBoot, Long>, CrudRepository<CarouselBoot, Long> {
    CarouselBoot findAllById(long id);
    List<CarouselBoot> findAllByStatus(int status);

}
