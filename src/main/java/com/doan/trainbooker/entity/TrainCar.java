package com.doan.trainbooker.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "traincar")
public class TrainCar { // Toa tàu
	@Id
	@Column(name = "id_train_car")
	Long id;

	@JoinColumn(name = "id_train")
	@ManyToOne(fetch = FetchType.LAZY)
	Train train; // Toa thuộc tàu nào

	@Column(name = "train_car_number")
	int trainCarNumber; // Số thự tự toa

	@JoinColumn(name = "id_train_car_type")
	@ManyToOne
	TrainCarType trainCarType;// Loại toa tàu

	@OneToMany(mappedBy = "trainCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<TrainSeat> trainSeats; // Các ghế trong toa
}
