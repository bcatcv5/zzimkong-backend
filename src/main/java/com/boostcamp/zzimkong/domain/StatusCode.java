package com.boostcamp.zzimkong.domain;

import lombok.Getter;

@Getter
public enum StatusCode {
    PROCESSING("진행 중"),
    FINISH("완료"),
    ERROR("에러");

    private final String status;

    StatusCode(String status) {
        this.status = status;
    }
}
