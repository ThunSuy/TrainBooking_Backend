package com.doan.trainbooker.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainRouteStationResponse {
	String nameStation;
	int stationOrder;
	double distance;
	LocalDateTime arrivalDuration;
	LocalDateTime departureDuration;
}
