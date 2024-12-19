package com.doan.trainbooker.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchBookingRequest {
	String reservationCode;
	String email; //Booker -> email
	String phone; //Booker -> phone
}
