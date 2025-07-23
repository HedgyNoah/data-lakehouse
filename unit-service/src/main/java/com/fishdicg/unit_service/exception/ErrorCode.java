package com.fishdicg.unit_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXIST(1008, "User not found", HttpStatus.NOT_FOUND),
    UNIT_NOT_EXIST(1009, "Unit not found", HttpStatus.NOT_FOUND),
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
