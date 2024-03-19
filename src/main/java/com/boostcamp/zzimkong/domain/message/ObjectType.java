package com.boostcamp.zzimkong.domain.message;

import lombok.Getter;

import java.util.Arrays;

import static com.boostcamp.zzimkong.utils.ZzimkongConstant.FURNITURE_TYPE;
import static com.boostcamp.zzimkong.utils.ZzimkongConstant.SPACE_TYPE;

@Getter
public enum ObjectType {
    False(SPACE_TYPE, false),
    True(FURNITURE_TYPE, true);

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
