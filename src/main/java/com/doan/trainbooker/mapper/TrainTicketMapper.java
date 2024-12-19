package com.doan.trainbooker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.doan.trainbooker.dto.request.TrainTicketCreationRequest;
import com.doan.trainbooker.dto.response.TrainTicketResponse;
import com.doan.trainbooker.entity.TrainTicket;

@Mapper(componentModel = "spring")
public interface TrainTicketMapper {
	TrainTicketMapper INSTANCE = Mappers.getMapper(TrainTicketMapper.class);

	TrainTicket toTrainTicket(TrainTicketCreationRequest request);

	@Mapping(source = "booking.reservationCode", target = "reservationCode")
	@Mapping(source = "trainTrip.train.trainCode", target = "trainCode")
	@Mapping(source = "startStation.trainStation.trainName", target = "nameStartStation")
	@Mapping(source = "endStation.trainStation.trainName", target = "nameEndStation")
	@Mapping(source = "startStation.arrivalDuration", target = "timeStart")
	@Mapping(source = "trainSeat.trainCar.trainCarNumber", target = "trainCarNumber")
	@Mapping(source = "trainSeat.trainSeatNumber", target = "trainSeatNumber")
	@Mapping(source = "trainSeat.trainCar.trainCarType.codeType", target = "codeType")
	TrainTicketResponse toTrainTicketResponse(TrainTicket trainTicket);
}
