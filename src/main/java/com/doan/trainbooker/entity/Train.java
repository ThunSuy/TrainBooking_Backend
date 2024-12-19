package com.doan.trainbooker.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "train")
public class Train { // Tàu
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_train")
	Long idTrain;

	@Column(name = "train_code", unique = true)
	String trainCode; // Mã tàu (Hiển thị ra ngoài)

	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<TrainCar> trainCars; // Danh sách các toa của tàu
}
