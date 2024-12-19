package com.doan.trainbooker.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doan.trainbooker.entity.Booking;
import com.doan.trainbooker.entity.TrainSeat;
import com.doan.trainbooker.entity.TrainTicket;
import com.doan.trainbooker.entity.TrainTrip;

@Repository
public interface TrainTicketRepository extends JpaRepository<TrainTicket, Long> {
//	// Lấy danh sách vé đã đặt cho một ghế cụ thể trong một chuyến tàu
//	List<TrainTicket> findByTrainSeatAndTrainTrip(TrainSeat trainSeat, TrainTrip trainTrip);

	// Lấy danh sách vé đã đặt cho một ghế cụ thể trong một chuyến tàu và có trạng
	// thái thanh toán là "PAID"
	@Query("SELECT tt FROM TrainTicket tt " + "WHERE tt.trainSeat = :trainSeat " + "AND tt.trainTrip = :trainTrip "
			+ "AND tt.booking.paymentStatus = 'PAID'")
	List<TrainTicket> findByTrainSeatAndTrainTrip(@Param("trainSeat") TrainSeat trainSeat,
			@Param("trainTrip") TrainTrip trainTrip);

	boolean existsByTicketCode(String ticketCode);

	@Query("""
			    SELECT t
			    FROM TrainTicket t
			    JOIN t.trainTrip tr
			    JOIN tr.train train
			    JOIN tr.trainRouteStations startStation
			    JOIN tr.trainRouteStations endStation
			    WHERE t.ticketCode = :codeTicket
			      AND train.trainCode = :trainCode
			      AND startStation.trainStation.trainName = :nameStartStation
			      AND endStation.trainStation.trainName = :nameEndStation

			      AND t.passengerName = :passengerName
			""")
	Optional<TrainTicket> findTrainTicketByCriteria(@Param("codeTicket") String codeTicket,
			@Param("trainCode") String trainCode, @Param("nameStartStation") String nameStartStation,
			@Param("nameEndStation") String nameEndStation, @Param("passengerName") String passengerName);

}
