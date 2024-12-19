package com.doan.trainbooker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.request.BookerCreationRequest;
import com.doan.trainbooker.dto.request.BookingCreationRequest;
import com.doan.trainbooker.dto.request.SearchTrainTicketRequest;
import com.doan.trainbooker.dto.request.TrainTicketCreationRequest;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.dto.response.BookingResponse;
import com.doan.trainbooker.dto.response.TrainTicketResponse;
import com.doan.trainbooker.service.services.BookerService;
import com.doan.trainbooker.service.services.BookingService;
import com.doan.trainbooker.service.services.TrainTicketService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {
	BookerService bookerService;
	BookingService bookingService;
	TrainTicketService trainTicketService;

	@PostMapping("/booker/add")
	public ApiResponse<BookerResponse> createBooker(@RequestBody @Valid BookerCreationRequest request) {
		return ApiResponse.<BookerResponse>builder().result(bookerService.createBooker(request)).build();
	}

	@PostMapping("/booking/add")
	public ApiResponse<BookingResponse> createBooking(@RequestBody @Valid BookingCreationRequest request) {
		return ApiResponse.<BookingResponse>builder().result(bookingService.createBooking(request)).build();
	}
	
	@GetMapping("/booking/find/{id}")
	public ApiResponse<BookingResponse> findBooking(@PathVariable Long id) {
		return ApiResponse.<BookingResponse>builder().result(bookingService.getBookingById(id)).build();
	}

	@PostMapping("/ticket/add")
	public ApiResponse<TrainTicketResponse> createTrainTicket(@RequestBody @Valid TrainTicketCreationRequest request) {
		return ApiResponse.<TrainTicketResponse>builder().result(trainTicketService.createTrainTicket(request)).build();
	}
	
	@PostMapping("/exists")
	public ApiResponse<Boolean> checkTrainTicketExists(@RequestBody SearchTrainTicketRequest request) {
	    boolean exists = trainTicketService.doesTrainTicketExist(request);
	    String message = exists ? "Vé hợp lệ" : "Vé không hợp lệ";
	    return ApiResponse.<Boolean>builder()
	        .result(exists)
	        .message(message)
	        .build();
	}
	
	@PostMapping("/booking/send-reservation-code")
	public ApiResponse<Void> sendReservationCode(@RequestParam String email) {
	    bookingService.sendReservationCodeByEmail(email);
	    return ApiResponse.<Void>builder()
	        .message("Mã đặt chỗ đã được gửi tới email " + email)
	        .build();
	}
}
