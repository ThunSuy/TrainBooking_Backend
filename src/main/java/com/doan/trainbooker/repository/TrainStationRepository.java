package com.doan.trainbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.TrainStation;

@Repository
public interface TrainStationRepository extends JpaRepository<TrainStation, Long> {

}
