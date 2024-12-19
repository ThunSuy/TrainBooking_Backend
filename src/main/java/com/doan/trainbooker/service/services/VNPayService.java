package com.doan.trainbooker.service.services;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
	public String createOrder(long total, String orderInfor, String urlReturn);
	public int orderReturn(HttpServletRequest request);
}
