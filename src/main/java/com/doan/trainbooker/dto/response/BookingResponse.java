package com.doan.trainbooker.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
	Long id;
	String reservationCode;
	BookerResponse bookerResponse;
	String paymentUrl;
	List<TrainTicketResponse> trainTickets;
	double totalPrice;
	String paymentStatus;
	LocalDateTime createdAt;
}
