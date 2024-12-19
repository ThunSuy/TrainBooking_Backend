package com.doan.trainbooker.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainTripResponse {
	Long idTrainTrip;
	Long idTrain;
	String trainCode;
	String direction;
	String nameStartStation;
	Long idRouteStartStation;
	String nameEndStation;
	Long idRouteEndStation;
	double distance;
	LocalDateTime departureTime;
	LocalDateTime endTime;
	String status;
	List<TrainRouteStationResponse> trainRouteStationResponses;
	

	private TrainDetail trainDetail; // Thông tin chi tiết tàu

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class TrainDetail {
		Long idTrain;
		String trainCode;
		List<TrainCarDetail> trainCars;
	}

	@Getter
	@Setter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class TrainCarDetail {
		Long id;
		int trainCarNumber;
		String trainCarTypeCode;
		List<TrainSeatResponse> trainSeats;
	}
}
