package com.fishdicg.notification_service.service;

import com.fishdicg.notification_service.dto.request.EmailRequest;
import com.fishdicg.notification_service.dto.request.SendEmailRequest;
import com.fishdicg.notification_service.dto.request.Sender;
import com.fishdicg.notification_service.dto.response.EmailResponse;
import com.fishdicg.notification_service.exception.AppException;
import com.fishdicg.notification_service.exception.ErrorCode;
import com.fishdicg.notification_service.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Fishdicg")
                        .email("contactnamhai@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .htmlContent(request.getHtmlContent())
                .subject(request.getSubject())
                .build();

        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException exception) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
