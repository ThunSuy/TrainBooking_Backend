package com.doan.trainbooker.service.implement;

import org.springframework.stereotype.Service;

import com.doan.trainbooker.dto.request.BookerCreationRequest;
import com.doan.trainbooker.dto.request.BookerUpdateRequest;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.mapper.BookerMapper;
import com.doan.trainbooker.repository.BookerRepository;
import com.doan.trainbooker.service.services.BookerService;
import com.doan.trainbooker.entity.Booker;
import com.doan.trainbooker.exception.AppException;
import com.doan.trainbooker.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookerServiceImpl implements BookerService {
	BookerRepository bookerRepository;
	BookerMapper bookerMapper;

	@Override
	public BookerResponse getBookerById(Long id) {
		Booker booker = bookerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));
		BookerResponse bookerResponse = bookerMapper.toBookerResponse(booker);
		return bookerResponse;
	}

	@Override
	public BookerResponse createBooker(BookerCreationRequest booker) {
		Booker result = bookerMapper.toBooker(booker);
		result = bookerRepository.save(result);
		BookerResponse bookerResponse = bookerMapper.toBookerResponse(result);
		return bookerResponse;

	}

	@Override
	public BookerResponse findOrCreateBooker(String email, String fullName) {
		BookerResponse bookerResponse = bookerMapper
				.toBookerResponse(bookerRepository.findByEmail(email).orElseGet(() -> {
					Booker newBooker = new Booker();
					newBooker.setEmail(email);
					newBooker.setFullName(fullName);
					newBooker.setCccd("123456789123");
					newBooker.setPhone("1234567890");
					return bookerRepository.save(newBooker);
				}));
		return bookerResponse;
	}

	@Override
	public BookerResponse updateBooker(Long id, BookerUpdateRequest bookerUpdateRequest) {
		Booker booker = bookerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));

		// Cập nhật các thông tin cần thiết
		if (bookerUpdateRequest.getFullName() != null) {
			booker.setFullName(bookerUpdateRequest.getFullName());
		}
		if (bookerUpdateRequest.getPhone() != null) {
			booker.setPhone(bookerUpdateRequest.getPhone());
		}
		if (bookerUpdateRequest.getCccd() != null) {
			booker.setCccd(bookerUpdateRequest.getCccd());
		}

		booker = bookerRepository.save(booker);

		return bookerMapper.toBookerResponse(booker);
	}

	@Override
	public boolean checkCccdAndPhone(String cccd, String phone) {
		return !"123456789123".equals(cccd) && "1234567890".equals(phone);
	}

	@Override
	public BookerResponse getBookerByEmail(String email) {
		Booker booker = bookerRepository.findByEmail(email)
	            .orElseThrow(() -> new AppException(ErrorCode.NO_BOOKER_FOUND));
	    
	    // Chuyển đổi Booker thành BookerResponse
	    BookerResponse bookerResponse = bookerMapper.toBookerResponse(booker);
	    
	    return bookerResponse;
	}
}
