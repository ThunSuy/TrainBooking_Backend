package com.doan.trainbooker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_booking")
	Long id;

	@Column(name = "reservation_code", unique = true)
	String reservationCode; // Mã đặt chỗ duy nhất

	@ManyToOne
	@JoinColumn(name = "id_booker")
	Booker booker; // Người đặt vé

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<TrainTicket> trainTickets; // Danh sách vé tàu liên kết với đặt chỗ này

	@Column(name = "total_price")
	double totalPrice; // Tổng chi phí đặt vé

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	PaymentStatus paymentStatus;

	@Column(name = "created_at")
	LocalDateTime createdAt;

	public enum PaymentStatus {
		PAID, UNPAID, CANCELLED
	}
}
