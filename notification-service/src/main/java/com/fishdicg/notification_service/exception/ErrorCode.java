package com.fishdicg.notification_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_SEND_EMAIL(1008, "Can not send email", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(10011, "You do not have permission", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1012, "Unauthenticated", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}
