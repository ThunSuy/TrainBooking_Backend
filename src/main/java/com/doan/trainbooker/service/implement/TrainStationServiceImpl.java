package com.doan.trainbooker.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doan.trainbooker.entity.TrainStation;
import com.doan.trainbooker.repository.TrainStationRepository;
import com.doan.trainbooker.service.services.TrainStationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainStationServiceImpl implements TrainStationService{
	TrainStationRepository trainStationRepository;

	@Override
	public List<TrainStation> getAllTrainStation() {
		return trainStationRepository.findAll();
	}
	
}
