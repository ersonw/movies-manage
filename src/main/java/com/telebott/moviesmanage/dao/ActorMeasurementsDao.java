package com.telebott.moviesmanage.dao;

import com.telebott.moviesmanage.entity.ActorMeasurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorMeasurementsDao extends JpaRepository<ActorMeasurements, Integer>, CrudRepository<ActorMeasurements, Integer> {
    ActorMeasurements findAllById(long id);
    List<ActorMeasurements> findAllByStatus(int status);
}
