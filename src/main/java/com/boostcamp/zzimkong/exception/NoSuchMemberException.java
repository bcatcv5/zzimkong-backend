package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class NoSuchMemberException extends ZzimkongException {
    public NoSuchMemberException(final Long userId) {
        super(
                String.format("존재하지 않은 회원입니다. userId={%d}", userId),
                "존재하지 않은 회원입니다",
                HttpStatus.BAD_REQUEST,
                "4002"
        );
    }
}
