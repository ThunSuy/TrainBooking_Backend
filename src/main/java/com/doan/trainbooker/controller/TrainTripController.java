package com.doan.trainbooker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.request.TrainTripCreationRequest;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.dto.response.TrainTripResponse;
import com.doan.trainbooker.entity.TrainStation;
import com.doan.trainbooker.exception.AppException;
import com.doan.trainbooker.exception.ErrorCode;
import com.doan.trainbooker.service.services.TrainStationService;
import com.doan.trainbooker.service.services.TrainTripService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/train-trip")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainTripController {
	TrainTripService trainTripService;
	TrainStationService trainStationService;

	@PostMapping("/add")
	public ApiResponse<TrainTripResponse> createTrainTrip(@RequestBody TrainTripCreationRequest request) {
		return ApiResponse.<TrainTripResponse>builder().result(trainTripService.createTrainTrip(request)).build();
	}

	@GetMapping("/search-train-trips")
	public ApiResponse<List<TrainTripResponse>> searchTrainTrips(@RequestParam Long departureStationId,
			@RequestParam Long arrivalStationId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate) {

		if (departureStationId == null || departureStationId <= 0 || arrivalStationId == null || arrivalStationId <= 0
				|| arrivalDate == null || arrivalDate.isBefore(LocalDate.now())) {
			throw new AppException(ErrorCode.INVALID_PARAMETER);
		}

		List<TrainTripResponse> trips = trainTripService.findAvailableTrips(departureStationId, arrivalStationId,
				arrivalDate);

		if (trips.isEmpty()) {
			throw new AppException(ErrorCode.NO_TRIPS_FOUND);
		}

		return ApiResponse.<List<TrainTripResponse>>builder().result(trips).build();
	}

	@GetMapping
	public ApiResponse<List<TrainStation>> getAllTrainStation() {
		return ApiResponse.<List<TrainStation>>builder().result(trainStationService.getAllTrainStation()).build();
	}
}
