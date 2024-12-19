package com.doan.trainbooker.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.doan.trainbooker.dto.request.BookingCreationRequest;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.dto.response.BookingResponse;
import com.doan.trainbooker.dto.response.HistoryBookingResponse;
import com.doan.trainbooker.dto.response.ListHistoryBookingResponse;
import com.doan.trainbooker.entity.Booking;

@Mapper(componentModel = "spring", uses = { BookerMapper.class, TrainTicketMapper.class })
public interface BookingMapper {
	BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

	Booking toBooking(BookingCreationRequest request);

	@Mapping(source = "booker", target = "bookerResponse")
	@Mapping(source = "trainTickets", target = "trainTickets")
	@Mapping(source = "paymentStatus", target = "paymentStatus", qualifiedByName = "paymentStatusToString")
	BookingResponse toBookingResponse(Booking booking);
	
	@Mapping(source = "trainTickets", target = "trainTickets")
    HistoryBookingResponse toHistoryBookingResponse(Booking booking);
	
	ListHistoryBookingResponse toListHistoryBookingResponse(BookerResponse bookerResponse, List<Booking> bookings);
	
	@Named("paymentStatusToString")
	default String paymentStatusToString(Booking.PaymentStatus paymentStatus) {
        return paymentStatus != null ? paymentStatus.name() : null;
    }
}
