package com.fishdicg.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXIST(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(1002, "Username already existed", HttpStatus.BAD_REQUEST),
    PASSWORD_SIZE_INVALID(1003, "Password needs to be at least 8 characters", HttpStatus.BAD_REQUEST),
    USERNAME_SIZE_INVALID(1004, "Username needs to be at least 3 characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1005, "Email is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1006, "Email already existed", HttpStatus.BAD_REQUEST),
    USERNAME_MISSING(1007, "Please enter username", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1008, "Role already existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1009, "Role already existed", HttpStatus.NOT_FOUND),
    FAILED_TO_CONNECT(10010, "Can not get response from the server", HttpStatus.BAD_REQUEST),
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
