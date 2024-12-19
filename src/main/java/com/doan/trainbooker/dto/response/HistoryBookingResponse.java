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
public class HistoryBookingResponse {
	Long id;
	String reservationCode;
	List<TrainTicketResponse> trainTickets;
	double totalPrice;
	LocalDateTime createdAt;
}
