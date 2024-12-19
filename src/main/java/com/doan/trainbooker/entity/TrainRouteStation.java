package com.doan.trainbooker.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "trainroutestation")
public class TrainRouteStation { // Ga tàu trên hành trình
	@Id
	@Column(name = "id_train_route_station")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "id_train_trip", nullable = false)
	TrainTrip trainTrip; // Thuộc chuyến tàu nào

	@ManyToOne
	@JoinColumn(name = "id_train_station", nullable = false)
	TrainStation trainStation;// Ga tàu

	@Column(name = "station_order", nullable = false)
	int stationOrder; // Số thứ tự của ga tàu trên hành trình

	@Column(name = "distance", nullable = false)
	double distance; // Khoảng cách từ ga đầu đến ga này

	@Column(name = "arrival_duration")
	private LocalDateTime arrivalDuration;

	@Column(name = "departure_duration")
	private LocalDateTime departureDuration;
}
