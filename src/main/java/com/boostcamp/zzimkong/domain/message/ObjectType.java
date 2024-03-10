package com.boostcamp.zzimkong.domain.message;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ObjectType {
    False("공간", false),
    True("가구", true);

    private final String type;

    private final Boolean objectType;

    ObjectType(String type, Boolean objectType) {
        this.type = type;
        this.objectType = objectType;
    }

    public static Boolean from(String type) {
        return Arrays.stream(values())
                .filter(objectType -> objectType.getType().equals(type))
                .findFirst()
                .orElseThrow()
                .getObjectType();
    }
}
