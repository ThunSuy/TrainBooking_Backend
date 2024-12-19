package com.doan.trainbooker.service.implement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doan.trainbooker.dto.request.TrainTripCreationRequest;
import com.doan.trainbooker.dto.response.TrainRouteStationResponse;
import com.doan.trainbooker.dto.response.TrainSeatResponse;
import com.doan.trainbooker.dto.response.TrainTripResponse;
import com.doan.trainbooker.entity.Train;
import com.doan.trainbooker.entity.TrainCar;
import com.doan.trainbooker.entity.TrainRouteStation;
import com.doan.trainbooker.entity.TrainTrip;
import com.doan.trainbooker.mapper.TrainRouteStationMapper;
import com.doan.trainbooker.mapper.TrainTripMapper;
import com.doan.trainbooker.repository.TrainRepository;
import com.doan.trainbooker.repository.TrainStationRepository;
import com.doan.trainbooker.repository.TrainTripRepository;
import com.doan.trainbooker.service.services.TrainTicketService;
import com.doan.trainbooker.service.services.TrainTripService;
import com.doan.trainbooker.utils.TicketPriceCalculator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainTripServiceImpl implements TrainTripService {
	TrainTripRepository trainTripRepository;
	TrainTripMapper trainTripMapper;
	TrainRouteStationMapper trainRouteStationMapper;
	TrainRepository trainRepository;
	TrainStationRepository trainStationRepository;
	TrainTicketService trainTicketService;
	TicketPriceCalculator ticketPriceCalculator;

	@Override
	public TrainTripResponse createTrainTrip(TrainTripCreationRequest request) {
		Train train = trainRepository.findById(request.getIdTrain())
				.orElseThrow(() -> new IllegalArgumentException("Train không tồn tại"));

		TrainTrip trainTrip = trainTripMapper.toTrainTrip(request);
		trainTrip.setTrain(train);
		trainTrip = trainTripRepository.save(trainTrip);

		TrainTripResponse result = trainTripMapper.toTrainTripResponse(trainTrip);
		result.setIdTrain(trainTrip.getTrain().getIdTrain());
		return result;
	}

	@Override
	public TrainTripResponse getTrainTripById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrainTripResponse> getAllTrainTrips() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrainTripResponse updateTrainTrip(Long id, TrainTripCreationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTrainTrip(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TrainTripResponse> findAvailableTrips(Long departureStationId, Long arrivalStationId,
			LocalDate departureTime) {
		List<TrainTrip> availableTrips = trainTripRepository.findAvailableTrips(departureStationId, arrivalStationId,
				departureTime);
		// Ten ga di, ga den
		String nameStartStation = trainStationRepository.findById(departureStationId).orElse(null).getTrainName();
		String nameEndStation = trainStationRepository.findById(arrivalStationId).orElse(null).getTrainName();

		return availableTrips.stream().map(trainTrip -> {

			// Lấy station_order của ga đi và ga đến
			int departureStationOrder = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(departureStationId))
					.map(TrainRouteStation::getStationOrder).findFirst().orElse(-1); // Hoặc giá trị mặc
																						// định nếu
																						// không tìm
																						// thấy

			int arrivalStationOrder = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(arrivalStationId))
					.map(TrainRouteStation::getStationOrder).findFirst().orElse(-1); // Hoặc giá trị mặc
																						// định nếu
																						// không tìm
																						// thấy

			// Lấy ra id của TrainRouteStation cho ga đi
			Long departureRouteStationId = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(departureStationId))
					.map(TrainRouteStation::getId).findFirst().orElse(null);

			// Lấy ra id của TrainRouteStation cho ga đến
			Long arrivalRouteStationId = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(arrivalStationId)).map(TrainRouteStation::getId)
					.findFirst().orElse(null);

			// arrivalDuration cua ga departureStationId
			Optional<LocalDateTime> arrivalDurationDeparture = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(departureStationId))
					.map(TrainRouteStation::getArrivalDuration).findFirst();

			// arrivalDuration cua ga arrivalStationId
			Optional<LocalDateTime> arrivalDurationArrival = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(arrivalStationId))
					.map(TrainRouteStation::getArrivalDuration).findFirst();

			// Tim distance cho ga departureStationId va arrivalStationId
			Optional<Double> distanceDeparture = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(departureStationId))
					.map(TrainRouteStation::getDistance).findFirst();

			Optional<Double> distanceArrival = trainTrip.getTrainRouteStations().stream()
					.filter(trs -> trs.getTrainStation().getId().equals(arrivalStationId))
					.map(TrainRouteStation::getDistance).findFirst();

			// Tinh distance
			Double distance = distanceArrival.orElse(0.0) - distanceDeparture.orElse(0.0);

			TrainTripResponse result = trainTripMapper.toTrainTripResponse(trainTrip);
			result.setDepartureTime(arrivalDurationDeparture.orElse(null));
			result.setEndTime(arrivalDurationArrival.orElse(null));
			result.setNameStartStation(nameStartStation);
			result.setIdRouteStartStation(departureRouteStationId);
			result.setNameEndStation(nameEndStation);
			result.setIdRouteEndStation(arrivalRouteStationId);
			result.setIdTrain(trainTrip.getTrain().getIdTrain());
			result.setTrainCode(trainTrip.getTrain().getTrainCode());
			result.setDistance(distance);
			
			// Thêm danh sách TrainRouteStationResponse
	        List<TrainRouteStationResponse> routeStations = trainTrip.getTrainRouteStations().stream()
	                .sorted(Comparator.comparing(TrainRouteStation::getStationOrder))
	                .map(trainRouteStationMapper::toTrainRouteStationResponse)
	                .collect(Collectors.toList());

	        result.setTrainRouteStationResponses(routeStations);
			// Map thông tin tàu
			TrainTripResponse.TrainDetail trainDetail = new TrainTripResponse.TrainDetail();
			trainDetail.setIdTrain(trainTrip.getTrain().getIdTrain());
			trainDetail.setTrainCode(trainTrip.getTrain().getTrainCode());

			// Map thông tin các toa tàu
			List<TrainTripResponse.TrainCarDetail> trainCars = trainTrip.getTrain().getTrainCars().stream()
					.sorted(Comparator.comparing(TrainCar::getTrainCarNumber)).map(trainCar -> {
						TrainTripResponse.TrainCarDetail trainCarDetail = new TrainTripResponse.TrainCarDetail();
						trainCarDetail.setId(trainCar.getId());
						trainCarDetail.setTrainCarNumber(trainCar.getTrainCarNumber());
						trainCarDetail.setTrainCarTypeCode(trainCar.getTrainCarType().getCodeType());

						// Map thông tin ghế trong toa
						List<TrainSeatResponse> trainSeats = trainCar.getTrainSeats().stream().map(trainSeat -> {
							TrainSeatResponse trainSeatDetail = new TrainSeatResponse();
							trainSeatDetail.setId(trainSeat.getId());
							trainSeatDetail.setTrainSeatNumber(trainSeat.getTrainSeatNumber());

							double basePrice = ticketPriceCalculator.calculateBasePrice(departureTime, distance,
									trainCar.getTrainCarType().getCodeType());
							trainSeatDetail.setPrice(basePrice);

							// Kiểm tra tình trạng của ghế
							boolean status = trainTicketService.isSeatAvailable(trainSeat, trainTrip,
									departureStationOrder, arrivalStationOrder);
							trainSeatDetail.setStatus(status);

							return trainSeatDetail;
						}).collect(Collectors.toList());

						trainCarDetail.setTrainSeats(trainSeats);
						return trainCarDetail;
					}).collect(Collectors.toList());

			trainDetail.setTrainCars(trainCars);
			result.setTrainDetail(trainDetail);

			return result;
		}).collect(Collectors.toList());
	}

}
