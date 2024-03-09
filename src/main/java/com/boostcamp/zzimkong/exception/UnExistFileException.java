package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class UnExistFileException extends ZzimkongException {
    public UnExistFileException() {
        super(
                "파일에 데이터가 존재하지 않습니다.",
                "파일에 데이터가 존재하지 않습니다.",
                HttpStatus.BAD_REQUEST,
                "4003"
        );
    }
}
