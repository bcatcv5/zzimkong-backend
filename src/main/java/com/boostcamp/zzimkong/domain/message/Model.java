package com.boostcamp.zzimkong.domain.message;

import lombok.Getter;

import java.util.Arrays;

import static com.boostcamp.zzimkong.utils.ZzimkongConstant.FURNITURE_TYPE;
import static com.boostcamp.zzimkong.utils.ZzimkongConstant.SPACE_TYPE;

@Getter
public enum Model {
    NERFACTO("nerfacto", SPACE_TYPE),
    OPENLRM("openlrm", FURNITURE_TYPE);

    private final String model;

    private final String type;

    Model(String model, String type) {
        this.model = model;
        this.type = type;
    }

    public static String from(String type) {
        return Arrays.stream(values())
                .filter(model -> model.getType().equals(type))
                .findFirst()
                .orElseThrow()
                .getModel();
    }
}
