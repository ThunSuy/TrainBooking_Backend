package com.doan.trainbooker.service.services;

import java.time.LocalDate;
import java.util.List;

import com.doan.trainbooker.dto.request.TrainTripCreationRequest;
import com.doan.trainbooker.dto.response.TrainTripResponse;

public interface TrainTripService {
	TrainTripResponse createTrainTrip(TrainTripCreationRequest request);
	TrainTripResponse getTrainTripById(Long id);

	List<TrainTripResponse> getAllTrainTrips();
	TrainTripResponse updateTrainTrip(Long id, TrainTripCreationRequest request);
	void deleteTrainTrip(Long id);
	List<TrainTripResponse> findAvailableTrips(Long departureStationId, Long arrivalStationId, LocalDate arrivalDate);
}