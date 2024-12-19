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
@Table(name = "booker")
public class Booker { // Người đặt vé
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booker")
    Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    String fullName;

    @Column(name = "email", nullable = false, length = 100)
    String email;

    @Column(name = "cccd", nullable = false, length = 12)
    String cccd;

    @Column(name = "phone", nullable = false, length = 15)
    String phone;
}
