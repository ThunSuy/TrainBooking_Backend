package com.doan.trainbooker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.entity.Booking;
import com.doan.trainbooker.service.services.BookingService;
import com.doan.trainbooker.service.services.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {

	VNPayService vnPayService;

	BookingService bookingService;

//    @GetMapping("/payment/create")
//    public ApiResponse<String> createPaymentUrl(
//            @RequestParam long amount,
//            @RequestParam String orderInfo,
//            HttpServletRequest request) {
//    	
//    	String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String vnpayUrl = vnPayService.createOrder(amount, orderInfo, baseUrl);
//        
//        return ApiResponse.<String>builder()
//                .result(vnpayUrl)
//                .message("Tạo URL thanh toán thành công")
//                .build();
//    }

//	@GetMapping("/payment-callback")
//	public ApiResponse<String> paymentCallback(@RequestParam Map<String, String> params) {
//		// Xử lý callback từ VNPay
//		// VD: Kiểm tra chữ ký (signature) và cập nhật trạng thái thanh toán trong
//		// database
//
//		// Đơn giản hóa, giả sử thanh toán thành công
//		return ApiResponse.<String>builder().message("Thanh toán thành công")
//				.result("Mã giao dịch: " + params.get("vnp_TransactionNo")).build();
//	}

	@GetMapping("/vnpay-payment")
	public ApiResponse<Map<String, Object>> handleVnPayCallback(HttpServletRequest request) {
		// Kiểm tra trạng thái thanh toán từ VNPay
		int paymentStatus = vnPayService.orderReturn(request);

		// Lấy các tham số từ request
		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");

		// Giả sử orderInfo là ID của booking, bạn có thể trích xuất từ vnp_OrderInfo
		Long bookingId = Long.parseLong(orderInfo.split(":")[1].trim());
		Booking booking = bookingService.getBookingEntity(bookingId);

		// Cập nhật trạng thái booking dựa trên kết quả thanh toán
		if (paymentStatus == 1) {
			bookingService.updateBookingStatus(bookingId, "PAID");
			bookingService.sendSuccessfulBooking(booking.getBooker().getEmail(), booking);
			System.out.println(booking.getBooker().getEmail());
		} else {
			bookingService.updateBookingStatus(bookingId, "CANCELLED");
		}

		// Tạo dữ liệu phản hồi
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("orderId", orderInfo);
		responseData.put("totalPrice", totalPrice);
		responseData.put("paymentTime", paymentTime);
		responseData.put("transactionId", transactionId);
		responseData.put("status", paymentStatus == 1 ? "SUCCESS" : "FAIL");

		// Trả về kết quả RESTful
		ApiResponse<Map<String, Object>> apiResponse = ApiResponse.<Map<String, Object>>builder()
				.message(paymentStatus == 1 ? "Payment successful" : "Payment failed").result(responseData).build();

		return apiResponse;
	}

}