package com.doan.trainbooker.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainTicketResponse {
	Long id;
	String ticketCode;
	String reservationCode; //booking -> reservationCode
	String trainCode; // TrainTrip -> Train ->trainCode
	String nameStartStation; //TrainRouteStaton -> TrainStation -> nameStation
	String nameEndStation; //TrainRouteStation -> TrainStation -> nameStation
	LocalDateTime timeStart; //TrainRouteStation -> arrival_duration
	int trainCarNumber; // TrainSeat -> TrainCar -> trainCarNumber
	int trainSeatNumber; // TrainSeat -> trainSeatNumber
	String codeType; // TrainSeat -> TrainCar -> TrainCarType -> codeType
	String passengerName;
	String passengerType;
	String cccd;
	LocalDate dateOfBirth;
	double finalPrice;
}
