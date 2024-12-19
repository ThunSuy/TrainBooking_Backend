package com.doan.trainbooker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.doan.trainbooker.entity.TrainTrip;

@Repository
public interface TrainTripRepository extends JpaRepository<TrainTrip, Long> {
	@EntityGraph(attributePaths = {"train.trainCars.trainSeats", "train.trainCars.trainCarType"})
	@Query("""
	        SELECT DISTINCT tt
	        FROM TrainTrip tt
	        JOIN tt.trainRouteStations trs1
	        JOIN tt.trainRouteStations trs2
	        WHERE trs1.trainStation.id = :departureStationId 
	        AND trs2.trainStation.id = :arrivalStationId
	        AND trs1.stationOrder < trs2.stationOrder
	        AND DATE(trs1.arrivalDuration) = :departureTime
	        AND tt.status = 'PENDING'
	        ORDER BY trs1.arrivalDuration
	    """)
	    List<TrainTrip> findAvailableTrips(
	        @Param("departureStationId") Long departureStationId,
	        @Param("arrivalStationId") Long arrivalStationId,
	        @Param("departureTime") LocalDate departureTime
	    );
}
