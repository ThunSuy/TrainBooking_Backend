package com.doan.trainbooker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.Booker;

@Repository
public interface BookerRepository extends JpaRepository<Booker, Long> {
	Optional<Booker> findByEmail(String email);
}
