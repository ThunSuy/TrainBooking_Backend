package com.doan.trainbooker.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainTripCreationRequest {
	Long idTrain;
	String direction;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime departureTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endTime;
	String status;
}
