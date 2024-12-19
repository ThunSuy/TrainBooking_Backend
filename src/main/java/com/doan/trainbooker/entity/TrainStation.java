package com.doan.trainbooker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "trainstation")
public class TrainStation { // Ga tàu
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_train_station")
	Long id;

	@Column(name = "name")
	String trainName; // Tên ga tàu

	@Column(name = "address")
	String address; // Địa chỉ
}
