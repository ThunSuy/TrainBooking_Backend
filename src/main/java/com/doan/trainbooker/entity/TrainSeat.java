package com.doan.trainbooker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "trainseat")
public class TrainSeat { // Ghế tàu
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_train_seat")
	Long id;

	@JoinColumn(name = "id_train_car")
	@ManyToOne(fetch = FetchType.LAZY)
	TrainCar trainCar; // Thuộc toa tàu nào

	@Column(name = "train_seat_number")
	int trainSeatNumber; // Số thứ tự của ghế trong toa
}
