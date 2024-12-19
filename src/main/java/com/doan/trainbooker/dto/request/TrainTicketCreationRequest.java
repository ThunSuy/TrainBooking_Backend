package com.doan.trainbooker.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainTicketCreationRequest {
	Long idBooking;
	Long idTrainTrip;
	Long idStartStation; // TrainRouteStation
	Long idEndStation;
	Long idTrainSeat;
	String passengerName;
	String passengerType;
	
	@NotBlank(message = "Số giấy tờ không được để trống, không được có khoảng cách")
	@Pattern(regexp = "^\\d{12}$", message = "Số giấy tờ bao gồm 12 số")
	String cccd;
	LocalDate dateOfBirth;
	double finalPrice;

}
