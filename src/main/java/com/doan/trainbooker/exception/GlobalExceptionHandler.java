package com.doan.trainbooker.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.doan.trainbooker.dto.response.ApiResponse;

import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());

		return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
	}

	// Bắt lỗi khi tham số đầu vào không đúng kiểu
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(ErrorCode.INVALID_PARAMETER.getCode());
		apiResponse.setMessage(
				"Invalid parameter: " + ex.getName() + " should be of type " + ex.getRequiredType().getSimpleName());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}
	
	
	// Validation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// Kết hợp tất cả lỗi thành một chuỗi, ngăn cách bởi dấu chấm phẩy
		String errorMessage = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.reduce((message1, message2) -> message1 + "; " + message2).orElse("Validation failed");

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(ErrorCode.INVALID_PARAMETER.getCode());
		apiResponse.setMessage(errorMessage);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
	}

}
