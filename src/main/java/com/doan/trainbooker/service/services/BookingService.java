package com.doan.trainbooker.service.services;

import com.doan.trainbooker.dto.request.BookingCreationRequest;
import com.doan.trainbooker.dto.request.CreateBookingRequest;
import com.doan.trainbooker.dto.request.SearchBookingRequest;
import com.doan.trainbooker.dto.response.BookingResponse;
import com.doan.trainbooker.dto.response.ListHistoryBookingResponse;
import com.doan.trainbooker.entity.Booking;

import jakarta.servlet.http.HttpServletRequest;

public interface BookingService {
	BookingResponse createBooking(BookingCreationRequest request);

	BookingResponse getBookingById(Long id);

	BookingResponse createBooking(CreateBookingRequest createBookingRequest, HttpServletRequest request);

	BookingResponse updateBookingStatus(Long bookingId, String newStatus);

	BookingResponse updateBookingTotalPrice(Long bookingId, double totalPrice);

	BookingResponse searchBooking(SearchBookingRequest request);
	
	void sendReservationCodeByEmail(String email);
	
	ListHistoryBookingResponse getBookingsByEmail(String email);

	void sendSuccessfulBooking(String email, Booking booking);

	Booking getBookingEntity(Long bookingId);
}
