package com.doan.trainbooker.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.request.BookerCreationRequest;
import com.doan.trainbooker.dto.request.BookerUpdateRequest;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.service.services.BookerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/booker")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookerController {
	BookerService bookerService;

	@PutMapping("/{id}")
	public ApiResponse<BookerResponse> updateBooker(@PathVariable Long id,
			@RequestBody BookerUpdateRequest bookerUpdateRequest) {
		BookerResponse updatedBooker = bookerService.updateBooker(id, bookerUpdateRequest);
		return ApiResponse.<BookerResponse>builder().result(updatedBooker).build();
	}
	
	@PostMapping("/check-cccd-phone")
    public ApiResponse<Boolean> checkCccdAndPhone(@RequestBody BookerCreationRequest bookerRequest) {
        boolean result = bookerService.checkCccdAndPhone(bookerRequest.getCccd(), bookerRequest.getPhone());
        return ApiResponse.<Boolean>builder().result(result).build();
    }
}
