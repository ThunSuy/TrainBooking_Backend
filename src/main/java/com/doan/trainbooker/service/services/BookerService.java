package com.doan.trainbooker.service.services;

import com.doan.trainbooker.dto.request.BookerCreationRequest;
import com.doan.trainbooker.dto.request.BookerUpdateRequest;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.dto.response.ListHistoryBookingResponse;

public interface BookerService {
	BookerResponse getBookerById(Long id);

	BookerResponse createBooker(BookerCreationRequest booker);

	BookerResponse findOrCreateBooker(String email, String fullName);

	BookerResponse updateBooker(Long id, BookerUpdateRequest bookerUpdateRequest);

	boolean checkCccdAndPhone(String cccd, String phone);

	BookerResponse getBookerByEmail(String email);

}
