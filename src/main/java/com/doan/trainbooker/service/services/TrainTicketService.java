package com.doan.trainbooker.service.services;

import com.doan.trainbooker.dto.request.SearchTrainTicketRequest;
import com.doan.trainbooker.dto.request.TrainTicketCreationRequest;
import com.doan.trainbooker.dto.response.TrainTicketResponse;
import com.doan.trainbooker.entity.TrainSeat;
import com.doan.trainbooker.entity.TrainTrip;

public interface TrainTicketService {
	boolean isSeatAvailable(TrainSeat trainSeat, TrainTrip trainTrip, int departureStationOrder,
			int arrivalStationOrder);

	TrainTicketResponse createTrainTicket(TrainTicketCreationRequest request);

	public boolean doesTrainTicketExist(SearchTrainTicketRequest request);
}
