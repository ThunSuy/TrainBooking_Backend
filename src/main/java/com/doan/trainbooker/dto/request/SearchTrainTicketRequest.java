package com.doan.trainbooker.dto.request;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchTrainTicketRequest {
	String codeTicket;
	String trainCode;			// TrainTrip -> Train -> trainCode
	String nameStartStation; 	// TrainRouteStaton -> TrainStation -> nameStation
	String nameEndStation; 		// TrainRouteStaton -> TrainStation -> nameStation
//	LocalDateTime timeStart; 	// TrainRouteStation -> arrival_duration
	String passengerName;
}
