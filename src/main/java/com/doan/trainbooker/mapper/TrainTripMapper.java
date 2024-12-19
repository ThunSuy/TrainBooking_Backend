package com.doan.trainbooker.mapper;

import org.mapstruct.Mapper;

import com.doan.trainbooker.dto.request.TrainTripCreationRequest;
import com.doan.trainbooker.dto.response.TrainTripResponse;
import com.doan.trainbooker.entity.TrainTrip;

@Mapper(componentModel = "spring")
public interface TrainTripMapper {
	TrainTripResponse toTrainTripResponse(TrainTrip trainTrip);

	TrainTrip toTrainTrip(TrainTripCreationRequest trainTripCreationRequest);
}
