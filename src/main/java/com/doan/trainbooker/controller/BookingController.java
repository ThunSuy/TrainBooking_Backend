package com.doan.trainbooker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.request.CreateBookingRequest;
import com.doan.trainbooker.dto.request.SearchBookingRequest;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.dto.response.BookingResponse;
import com.doan.trainbooker.dto.response.ListHistoryBookingResponse;
import com.doan.trainbooker.service.services.BookingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
	BookingService bookingService;

	@PostMapping("/add")
	public ApiResponse<BookingResponse> createBooking(@RequestBody CreateBookingRequest createBookingRequest, HttpServletRequest request) {
		return ApiResponse.<BookingResponse>builder().result(bookingService.createBooking(createBookingRequest, request))
				.build();
	}

	@PostMapping("/search")
	public ApiResponse<BookingResponse> searchBooking(@RequestBody SearchBookingRequest request) {
		BookingResponse bookingResponse = bookingService.searchBooking(request);
		return ApiResponse.<BookingResponse>builder().result(bookingResponse)
				.build();
	}
	
	@PostMapping("/send-reservation-code")
	public ApiResponse<Void> sendReservationCode(@RequestParam String email) {
	    bookingService.sendReservationCodeByEmail(email);
	    return ApiResponse.<Void>builder()
	        .message("Mã đặt chỗ đã được gửi tới email " + email)
	        .build();
	}
	
	@GetMapping("/history")
    public ApiResponse<ListHistoryBookingResponse> getBookingHistory(@RequestParam String email) {
        return ApiResponse.<ListHistoryBookingResponse>builder().result(bookingService.getBookingsByEmail(email)).build();
    }
}
