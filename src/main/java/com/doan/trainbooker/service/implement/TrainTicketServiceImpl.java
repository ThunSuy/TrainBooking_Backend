package com.doan.trainbooker.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doan.trainbooker.dto.request.SearchTrainTicketRequest;
import com.doan.trainbooker.dto.request.TrainTicketCreationRequest;
import com.doan.trainbooker.dto.response.TrainTicketResponse;
import com.doan.trainbooker.entity.Booking;
import com.doan.trainbooker.entity.TrainRouteStation;
import com.doan.trainbooker.entity.TrainSeat;
import com.doan.trainbooker.entity.TrainTicket;
import com.doan.trainbooker.entity.TrainTrip;
import com.doan.trainbooker.exception.AppException;
import com.doan.trainbooker.exception.ErrorCode;
import com.doan.trainbooker.mapper.TrainTicketMapper;
import com.doan.trainbooker.repository.BookingRepository;
import com.doan.trainbooker.repository.TrainRouteStationRepository;
import com.doan.trainbooker.repository.TrainSeatRepository;
import com.doan.trainbooker.repository.TrainTicketRepository;
import com.doan.trainbooker.repository.TrainTripRepository;
import com.doan.trainbooker.service.services.TrainTicketService;
import com.doan.trainbooker.utils.CodeGenerator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainTicketServiceImpl implements TrainTicketService {
	TrainTicketRepository trainTicketRepository;
	BookingRepository bookingRepository;
	TrainTripRepository trainTripRepository;
	TrainRouteStationRepository trainRouteStationRepository;
	TrainSeatRepository trainSeatRepository;

	TrainTicketMapper trainTicketMapper;

	@Override
	public boolean isSeatAvailable(TrainSeat trainSeat, TrainTrip trainTrip, int departureStationOrder,
			int arrivalStationOrder) {
		List<TrainTicket> bookedTickets = trainTicketRepository.findByTrainSeatAndTrainTrip(trainSeat, trainTrip);

		// Nếu không có vé nào được đặt cho ghế này trong chuyến tàu, thì ghế trống
		if (bookedTickets.isEmpty()) {
			return true;
		}

		// Duyệt qua danh sách vé đã đặt để kiểm tra xem có trùng khoảng ga đi - đến
		// không
		for (TrainTicket ticket : bookedTickets) {
			int bookedDepartureOrder = ticket.getStartStation().getStationOrder();
			int bookedArrivalOrder = ticket.getEndStation().getStationOrder();
			// Kiểm tra điều kiện trùng chỗ giữa các ga đi - đến
			if (!(arrivalStationOrder <= bookedDepartureOrder || departureStationOrder >= bookedArrivalOrder)) {
				return false; // Ghế đã được đặt trong khoảng thời gian trùng
			}
		}

		return true; // Ghế không bị trùng chỗ trong khoảng thời gian yêu cầu
	}

	@Override
	public TrainTicketResponse createTrainTicket(TrainTicketCreationRequest request) {
		// Request -> Entity
		String ticketCode = "";
		do {
			ticketCode = CodeGenerator.generateTicketCode();
		} while (trainTicketRepository.existsByTicketCode(ticketCode));

		Booking booking = bookingRepository.findById(request.getIdBooking())
				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKING_FOUND));

		TrainTrip trainTrip = trainTripRepository.findById(request.getIdTrainTrip())
				.orElseThrow(() -> new AppException(ErrorCode.NO_TRAINTRIP_FOUND));

		TrainRouteStation startStation = trainRouteStationRepository.findById(request.getIdStartStation())
				.orElseThrow(() -> new AppException(ErrorCode.NO_TRAINROUTE_FOUND));

		TrainRouteStation endStation = trainRouteStationRepository.findById(request.getIdEndStation())
				.orElseThrow(() -> new AppException(ErrorCode.NO_TRAINROUTE_FOUND));

		TrainSeat trainSeat = trainSeatRepository.findById(request.getIdTrainSeat())
				.orElseThrow(() -> new AppException(ErrorCode.NO_TRAINSEAT_FOUND));

		if (!isSeatAvailable(trainSeat, trainTrip, startStation.getStationOrder(), endStation.getStationOrder())) {
			return null;
		}

		TrainTicket trainTicket = trainTicketMapper.toTrainTicket(request);
		trainTicket.setTicketCode(ticketCode);
		trainTicket.setBooking(booking);
		trainTicket.setTrainTrip(trainTrip);
		trainTicket.setStartStation(startStation);
		trainTicket.setEndStation(endStation);
		trainTicket.setTrainSeat(trainSeat);
		trainTicket = trainTicketRepository.save(trainTicket);

		// Entity -> Response
		TrainTicketResponse response = trainTicketMapper.toTrainTicketResponse(trainTicket);
//		response.setReservationCode(trainTicket.getBooking().getReservationCode());
//		response.setTrainCode(trainTicket.getTrainTrip().getTrain().getTrainCode());
//		response.setNameStartStation(trainTicket.getStartStation().getTrainStation().getTrainName());
//		response.setNameEndStation(trainTicket.getEndStation().getTrainStation().getTrainName());
//		response.setTimeStart(trainTicket.getStartStation().getArrivalDuration());
//		response.setTrainSeatNumber(trainTicket.getTrainSeat().getTrainSeatNumber());
//		response.setCodeType(trainTicket.getTrainSeat().getTrainCar().getTrainCarType().getCodeType());
//		response.setTrainCarNumber(trainTicket.getTrainSeat().getTrainCar().getTrainCarNumber());

		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public boolean doesTrainTicketExist(SearchTrainTicketRequest request) {
		Optional<TrainTicket> ticket = trainTicketRepository.findTrainTicketByCriteria(
	            request.getCodeTicket(),
	            request.getTrainCode(),
	            request.getNameStartStation(),
	            request.getNameEndStation(),
//	            request.getTimeStart(),
	            request.getPassengerName()
	        );
	        return ticket.isPresent();
	}

}
