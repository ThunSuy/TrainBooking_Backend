package com.doan.trainbooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.TrainSeat;

@Repository
public interface TrainSeatRepository extends JpaRepository<TrainSeat, Long> {

}
