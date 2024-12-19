package com.doan.trainbooker.service.implement;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.doan.trainbooker.exception.AppException;
import com.doan.trainbooker.exception.ErrorCode;
import com.doan.trainbooker.service.services.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
	JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
	}

}
