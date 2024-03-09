package com.boostcamp.zzimkong.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ZzimkongException extends RuntimeException {
    private final String showMessage;
    private final HttpStatus httpStatus;
    private final String errorCode;

    public ZzimkongException(final String message,
                          final String showMessage,
                          final HttpStatus httpStatus,
                          final String errorCode) {
        super(message);
        this.showMessage = showMessage;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
