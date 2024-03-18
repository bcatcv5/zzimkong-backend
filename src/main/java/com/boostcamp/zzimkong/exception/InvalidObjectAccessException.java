package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class InvalidObjectAccessException extends ZzimkongException {
    public InvalidObjectAccessException() {
        super(
                "객체 접근 권한이 없습니다.",
                "객체 접근 권한이 없습니다.",
                HttpStatus.BAD_REQUEST,
                "4010"
        );
    }
}
