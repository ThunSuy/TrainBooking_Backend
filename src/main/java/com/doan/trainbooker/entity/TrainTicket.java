package com.doan.trainbooker.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "trainticket")
public class TrainTicket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_train_ticket")
	Long id;

	@Column(name = "ticket_code", nullable = false, unique = true)
	String ticketCode; // Mã vé duy nhất

	@ManyToOne
	@JoinColumn(name = "id_booking", nullable = false)
	Booking booking; // Liên kết vé với đặt chỗ

	@ManyToOne
	@JoinColumn(name = "id_train_trip", nullable = false)
	TrainTrip trainTrip; // Liên kết với chuyến tàu

	@ManyToOne
	@JoinColumn(name = "id_start_station", nullable = false)
	TrainRouteStation startStation; // Ga bắt đầu hành trình của vé

	@ManyToOne
	@JoinColumn(name = "id_end_station", nullable = false)
	TrainRouteStation endStation; // Ga kết thúc hành trình của vé

	@ManyToOne
	@JoinColumn(name = "id_train_seat", nullable = false)
	TrainSeat trainSeat; // Liên kết ghế cho vé này

	@Column(name = "price")
	double finalPrice; // Giá vé

	// Tên người dùng vé
	@Column(name = "passenger_name", nullable = false)
	String passengerName;

	// Đối tượng người dùng vé (người lớn, người già, trẻ em)
	@Enumerated(EnumType.STRING)
	@Column(name = "passenger_type", nullable = false)
	PassengerType passengerType;

	// CCCD nếu là người lớn hoặc người già
	@Column(name = "cccd")
	String cccd;

	// Ngày tháng năm sinh nếu là trẻ em
	@Column(name = "date_of_birth")
	LocalDate dateOfBirth;

	public enum PassengerType {
		ADULT, CHILD, SENIOR
	}
}
