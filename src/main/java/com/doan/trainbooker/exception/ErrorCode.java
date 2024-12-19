package com.doan.trainbooker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
	NO_TRIPS_FOUND(1001, "Không tìm thấy chuyến tàu nào theo tiêu chí đã chỉ định.", HttpStatus.NOT_FOUND),
	INVALID_PARAMETER(1002, "Tham số đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),
	RESREVATION_CODE_EXISTED(1003, "Mã đặt chỗ đã tồn tại", HttpStatus.BAD_REQUEST),
	NO_BOOKER_FOUND(1004, "Không tìm thấy người đặt vé", HttpStatus.NOT_FOUND),
	NO_BOOKING_FOUND(1005, "Không tìm thấy hồ sơ đặt vé này", HttpStatus.NOT_FOUND),
	NO_TRAINTRIP_FOUND(1006, "Không tìm thấy chuyến tàu", HttpStatus.NOT_FOUND),
	NO_TRAINROUTE_FOUND(1007, "Không tìm thấy điểm ga trên hành trình", HttpStatus.NOT_FOUND),
	NO_TRAINSEAT_FOUND(1008, "Không tìm thấy ghế", HttpStatus.NOT_FOUND),
	SEAT_ALREADY_BOOKED(1009, "Ghế đã được đặt", HttpStatus.CONFLICT),
	EMAIL_SEND_FAILED(1010, "Mail gửi thất bại", HttpStatus.BAD_REQUEST)
	;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}

	private final int code;
	private final String message;
	private final HttpStatusCode statusCode;
}
