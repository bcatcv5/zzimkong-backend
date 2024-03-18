package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class NoSuchFileUrlException extends ZzimkongException {
    public NoSuchFileUrlException() {
        super(
                "파일 주소가 올바르지 않습니다.",
                "파일 주소가 올바르지 않습니다.",
                HttpStatus.BAD_REQUEST,
                "4011"
        );
    }
}
