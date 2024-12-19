package com.doan.trainbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.TrainRouteStation;

@Repository
public interface TrainRouteStationRepository extends JpaRepository<TrainRouteStation, Long>{

}
