package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class InvalidDurationException extends ZzimkongException {
    public InvalidDurationException() {
        super(
                "영상 길이는 2분 이상 10분 이하만 가능합니다.",
                "영상 길이는 2분 이상 10분 이하만 가능합니다.",
                HttpStatus.BAD_REQUEST,
                "4004"
        );
    }
}