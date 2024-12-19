package com.doan.trainbooker.service.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}