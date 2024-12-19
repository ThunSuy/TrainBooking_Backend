package com.doan.trainbooker.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doan.trainbooker.dto.request.BookingCreationRequest;
import com.doan.trainbooker.dto.request.CreateBookingRequest;
import com.doan.trainbooker.dto.request.SearchBookingRequest;
import com.doan.trainbooker.dto.request.TrainTicketCreationRequest;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.dto.response.BookingResponse;
import com.doan.trainbooker.dto.response.HistoryBookingResponse;
import com.doan.trainbooker.dto.response.ListHistoryBookingResponse;
import com.doan.trainbooker.dto.response.TrainTicketResponse;
import com.doan.trainbooker.entity.Booker;
import com.doan.trainbooker.entity.Booking;
import com.doan.trainbooker.entity.Booking.PaymentStatus;
import com.doan.trainbooker.exception.AppException;
import com.doan.trainbooker.exception.ErrorCode;
import com.doan.trainbooker.mapper.BookerMapper;
import com.doan.trainbooker.mapper.BookingMapper;
import com.doan.trainbooker.repository.BookerRepository;
import com.doan.trainbooker.repository.BookingRepository;
import com.doan.trainbooker.repository.TrainTicketRepository;
import com.doan.trainbooker.service.services.BookerService;
import com.doan.trainbooker.service.services.BookingService;
import com.doan.trainbooker.service.services.EmailService;
import com.doan.trainbooker.service.services.TrainTicketService;
import com.doan.trainbooker.service.services.VNPayService;
import com.doan.trainbooker.utils.CodeGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
	BookingRepository bookingRepository;
	BookingMapper bookingMapper;
	BookerRepository bookerRepository;
	BookerMapper bookerMapper;
	TrainTicketRepository trainTicketRepository;
	BookerService bookerService;
	TrainTicketService trainTicketService;
	EmailService emailService;
	VNPayService vnPayService;
	
	@Override
	public void sendSuccessfulBooking(String email, Booking booking) {
	    String subject = "Thanh toán thành công";
	    String template = """
	        Kính gửi quý Khách hàng,

	        Xin trân trọng cảm ơn quý khách đã lựa chọn sử dụng dịch vụ của Tổng công ty Đường Sắt Việt Nam.
	        Chúng tôi xin thông báo quý khách đã thực hiện đặt vé thành công với thông tin sau:

	        Mã đặt chỗ: %s
	        Tổng số vé: %d
	        Tổng số tiền: %.1f VNĐ

	        Trân trọng,
	        Tổng công ty Đường Sắt Việt Nam.
	        """;

	    String emailContent = String.format(
	            template,
	            booking.getReservationCode(),
	            booking.getTrainTickets().size(),
	            booking.getTotalPrice()
	    );

	    emailService.sendEmail(email, subject, emailContent);
	}

	@Override
	public BookingResponse createBooking(BookingCreationRequest request) {
		String reservationCode = "";
		do {
			reservationCode = CodeGenerator.generateReservationCode();
		} while (bookingRepository.existsByReservationCode(reservationCode));

		Booker booker = bookerRepository.findById(request.getIdBooker())
				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));

		Booking temp = bookingMapper.toBooking(request);
		temp.setReservationCode(reservationCode);
		temp.setBooker(booker);
		temp.setCreatedAt(LocalDateTime.now());
		temp.setPaymentStatus(PaymentStatus.UNPAID);
		temp = bookingRepository.save(temp);

		BookingResponse result = bookingMapper.toBookingResponse(temp);
		result.setBookerResponse(bookerMapper.toBookerResponse(booker));

		return result;
	}

	@Override
	public BookingResponse getBookingById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKING_FOUND));

		BookingResponse bookingResponse = bookingMapper.toBookingResponse(booking);

		return bookingResponse;
	}

//	@Override
//	public BookingResponse createBooking(CreateBookingRequest createBookingRequest) {
//		double totalPrice = 0;
//		BookerResponse bookerResponse = bookerService.createBooker(createBookingRequest.getBookerRequest());
//		BookingCreationRequest bookingRequest = new BookingCreationRequest();
//		bookingRequest.setIdBooker(bookerResponse.getId());
//		BookingResponse bookingResponse = createBooking(bookingRequest);
//
//		List<TrainTicketResponse> listTrainTicketResponse = new ArrayList<>();
//		for (TrainTicketCreationRequest ticket : createBookingRequest.getListTrainTicketRequest()) {
//			ticket.setIdBooking(bookingResponse.getId());
//			TrainTicketResponse ticketResponse = trainTicketService.createTrainTicket(ticket);
//			System.out.println(ticketResponse);
//			if (ticketResponse == null) {
//				for (TrainTicketResponse ticketrp : listTrainTicketResponse) {
//					trainTicketRepository.deleteById(ticketrp.getId());
//				}
//				bookingRepository.deleteById(bookingResponse.getId());
//				bookerRepository.deleteById(bookerResponse.getId());
//				throw new AppException(ErrorCode.SEAT_ALREADY_BOOKED);
//			} else {
//				listTrainTicketResponse.add(ticketResponse);
//				totalPrice += ticket.getFinalPrice();
//			}
//		}
//
//		bookingResponse.setBookerResponse(bookerResponse);
//		bookingResponse = updateBookingTotalPrice(bookingResponse.getId(), totalPrice);
//		bookingResponse.setTrainTickets(listTrainTicketResponse);
//
//		return bookingResponse;
//	}

	@Transactional
	@Override
	public BookingResponse createBooking(CreateBookingRequest createBookingRequest, HttpServletRequest request) {
		double totalPrice = 0;

		// Tao booker
		BookerResponse bookerResponse = bookerService.getBookerById(createBookingRequest.getBookerId());

		// Tao booking
		BookingCreationRequest bookingRequest = new BookingCreationRequest();
		bookingRequest.setIdBooker(bookerResponse.getId());
		BookingResponse bookingResponse = createBooking(bookingRequest);

		// Danh sach ve
		List<TrainTicketResponse> listTrainTicketResponse = new ArrayList<>();

		try {
			// Xu ly tung TrainTicketCreationRequest
			for (TrainTicketCreationRequest ticket : createBookingRequest.getListTrainTicketRequest()) {
				ticket.setIdBooking(bookingResponse.getId());
				TrainTicketResponse ticketResponse = trainTicketService.createTrainTicket(ticket);

				if (ticketResponse == null) {
					throw new AppException(ErrorCode.SEAT_ALREADY_BOOKED);
				}

				listTrainTicketResponse.add(ticketResponse);
				totalPrice += ticket.getFinalPrice();
			}

			// Cap nhat Booker va Booking
			bookingResponse.setBookerResponse(bookerResponse);
			bookingResponse = updateBookingTotalPrice(bookingResponse.getId(), totalPrice);
			bookingResponse.setTrainTickets(listTrainTicketResponse);

			// Call vnpay
			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

			String paymentUrl = vnPayService.createOrder(Math.round(totalPrice),
					"Booking ID: " + bookingResponse.getId(), baseUrl);

			bookingResponse.setPaymentUrl(paymentUrl);

			return bookingResponse;

		} catch (AppException e) {
			// Rollback logic khi xảy ra lỗi (Hibernate tự rollback vì @Transactional)
			throw e;
		}
	}

	@Override
	public BookingResponse updateBookingStatus(Long bookingId, String newStatus) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKING_FOUND));
		Booking.PaymentStatus paymentStatus = Booking.PaymentStatus.valueOf(newStatus);

		booking.setPaymentStatus(paymentStatus);
		bookingRepository.save(booking);

		return bookingMapper.toBookingResponse(booking);
	}

	@Override
	public BookingResponse updateBookingTotalPrice(Long bookingId, double totalPrice) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKING_FOUND));

		booking.setTotalPrice(totalPrice);
		bookingRepository.save(booking);

		return bookingMapper.toBookingResponse(booking);
	}

	@Override
	public BookingResponse searchBooking(SearchBookingRequest request) {
		Booking booking = bookingRepository.findFirstByReservationCodeAndBookerEmailAndBookerPhone(
				request.getReservationCode(), request.getEmail(), request.getPhone());

		if (booking == null) {
			throw new AppException(ErrorCode.NO_BOOKING_FOUND);
		}

		return bookingMapper.toBookingResponse(booking);
	}

	@Override
	public void sendReservationCodeByEmail(String email) {
//		Booker booker = bookerRepository.findByEmail(email)
//				.orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));

		List<String> reservationCodes = bookingRepository.findAllReservationCodesByEmail(email);
		System.out.println(reservationCodes);
		if (reservationCodes.isEmpty()) {
			throw new AppException(ErrorCode.NO_BOOKING_FOUND);
		}

		String subject = "Danh sách mã đặt chỗ của bạn";
		String body = "Xin chào ,\n\n" + "Dưới đây là danh sách mã đặt chỗ của bạn:\n"
				+ String.join("\n", reservationCodes) + "\n\nCảm ơn bạn đã sử dụng dịch vụ của chúng tôi.";

		emailService.sendEmail(email, subject, body);
	}

	@Override
	public ListHistoryBookingResponse getBookingsByEmail(String email) {
		Booker booker = bookerRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));

        BookerResponse bookerResponse = bookerMapper.toBookerResponse(booker);

        
        List<Booking> bookings = bookingRepository.findByBookerEmailAndPaymentStatus(email, Booking.PaymentStatus.PAID);
        List<HistoryBookingResponse> list = new ArrayList<HistoryBookingResponse>();
        for (Booking booking : bookings) {
			list.add(bookingMapper.toHistoryBookingResponse(booking));
		}
        
        ListHistoryBookingResponse rs = new ListHistoryBookingResponse();
        rs.setBookerResponse(bookerResponse);
        rs.setList(list);
        
        return rs;
	}

	@Override
	public Booking getBookingEntity(Long id) {
	    return bookingRepository.findById(id)
	            .orElseThrow(() -> new AppException(ErrorCode.NO_BOOKING_FOUND));
	}
}
