package com.doan.trainbooker.mapper;

import org.mapstruct.Mapper;

import com.doan.trainbooker.dto.request.BookerCreationRequest;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.entity.Booker;

@Mapper(componentModel = "spring")
public interface BookerMapper {
	BookerResponse toBookerResponse(Booker booker);
	
	Booker toBooker(BookerCreationRequest booker);
}
