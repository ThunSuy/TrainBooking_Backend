package com.doan.trainbooker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.doan.trainbooker.dto.response.TrainRouteStationResponse;
import com.doan.trainbooker.entity.TrainRouteStation;

@Mapper(componentModel = "spring")
public interface TrainRouteStationMapper {
	@Mapping(target = "nameStation", source = "trainStation.trainName")
	TrainRouteStationResponse toTrainRouteStationResponse(TrainRouteStation trainRouteStation);
}
