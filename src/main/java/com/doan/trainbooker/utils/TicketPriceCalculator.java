package com.doan.trainbooker.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class TicketPriceCalculator {

	private static final double BASE_PRICE = 150.0;
	private static final double DISTANCE_COST_RATE = 0.45;
//	private static final double CHILD_DISCOUNT = 0.5; // Trẻ em được giảm 50%
//	private static final double SENIOR_DISCOUNT = 0.2; // Người cao tuổi được giảm 20%
	private static final double TRAIN_CAR_NM56 = 1.0;
	private static final double TRAIN_CAR_GN42 = 1.35;
	private static final double TRAIN_CAR_GN28 = 1.7;

	public int calculateBasePrice(LocalDate bookingDate, double distance, String codeType) {
		double basePrice = BASE_PRICE;

		// Gia theo quang duong
		basePrice += distance * DISTANCE_COST_RATE;

		// Gia theo loai tau
		if (codeType.equals("NM56"))
			basePrice *= TRAIN_CAR_NM56;
		if (codeType.equals("GN42"))
			basePrice *= TRAIN_CAR_GN42;
		if (codeType.equals("GN28"))
			basePrice *= TRAIN_CAR_GN28;

		// Gia theo ngay dat
		long daysToDeparture = Duration.between(bookingDate.atTime(0, 0), LocalDateTime.now()).toDays();
		if (daysToDeparture < 4) {
			if (daysToDeparture <= 2)
				basePrice += 100;
			else
				basePrice += 50;
		}

		return (int) (basePrice);
	}

//	public double applyDiscounts(double basePrice, String ticketType) {
//		if ("CHILD".equals(ticketType)) {
//			return basePrice * 0.5; // Giảm 50% cho trẻ em
//		} else if ("SENIOR".equals(ticketType)) {
//			return basePrice * 0.8; // Giảm 20% cho người cao tuổi
//		}
//		return basePrice;
//	}
}
