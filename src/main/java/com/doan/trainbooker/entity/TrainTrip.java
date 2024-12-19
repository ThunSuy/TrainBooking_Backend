package com.doan.trainbooker.entity;

import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "traintrip")
@Entity
public class TrainTrip { // Chuyến tàu
	@Id
	@Column(name = "id_train_trip")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long idTrainTrip;
	
	@ManyToOne
	@JoinColumn(name = "id_train", unique = true)
	Train train; // Tàu nào chạy tuyến này

	@Column(name = "direction")
	@Enumerated(EnumType.STRING)
	Direction direction;

	@Column(name = "departure_time")
	LocalDateTime departureTime;

	@Column(name = "end_time")
	LocalDateTime endTime;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	Status status;

	@OneToMany(mappedBy = "trainTrip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<TrainRouteStation> trainRouteStations; // Danh sách các ga sẽ đi qua trong hành trình

	public enum Direction {
		NorthSouth, SouthNorth;
	}

	public enum Status {
		PENDING, COMPLETED;
	}
}
