package com.doan.trainbooker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	boolean existsByReservationCode(String reservationCode);

	Booking findFirstByReservationCodeAndBookerEmailAndBookerPhone(String reservationCode, String email, String phone);

	@Query("SELECT b.reservationCode FROM Booking b WHERE b.booker.email = :email")
	List<String> findAllReservationCodesByEmail(@Param("email") String email);
	
	List<Booking> findByBookerEmailAndPaymentStatus(String email, Booking.PaymentStatus status);
}
