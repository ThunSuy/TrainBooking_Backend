package com.doan.trainbooker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "traincartype")
public class TrainCarType { // Loại toa tàu
	@Id
	@Column(name = "id_train_car_type")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "code_type", unique = true)
	String codeType; // Mã loại tàu (Hiển thị ra ngoài)

	@Column(name = "description")
	String description; // Mô tả

	@Column(name = "number_of_train_seat")
	int numberOfTrainSeat; // Số lượng ghế trong từng loại toa tàu
}
