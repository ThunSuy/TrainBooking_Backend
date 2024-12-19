package com.doan.trainbooker.dto.request;

import lombok.Data;
import java.util.List;

import jakarta.validation.Valid;

@Data
public class CreateBookingRequest {
    
    private Long bookerId;
    
    private List<TrainTicketCreationRequest> listTrainTicketRequest;
}