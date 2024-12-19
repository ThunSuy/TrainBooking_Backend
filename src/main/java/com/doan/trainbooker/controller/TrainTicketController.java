package com.doan.trainbooker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.request.SearchTrainTicketRequest;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.service.services.TrainTicketService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainTicketController {
	TrainTicketService trainTicketService;
	
	@PostMapping("/exists")
	public ApiResponse<Boolean> checkTrainTicketExists(@RequestBody SearchTrainTicketRequest request) {
	    boolean exists = trainTicketService.doesTrainTicketExist(request);
	    return ApiResponse.<Boolean>builder()
	        .result(exists)
	        .message(exists ? "Vé hợp lệ" : "Vé không hợp lệ")
	        .build();
	}
}
