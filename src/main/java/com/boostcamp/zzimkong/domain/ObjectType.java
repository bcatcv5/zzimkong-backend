package com.boostcamp.zzimkong.domain;

import lombok.Getter;

@Getter
public enum ObjectType {
    SPACE(false),
    FURNITURE(true);

    private final boolean objectType;

    ObjectType(boolean objectType) {
        this.objectType = objectType;
    }
}
