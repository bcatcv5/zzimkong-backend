package com.boostcamp.zzimkong.domain.message;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Model {
    NERFACTO("nerfacto", "공간"),
    OPENLRM("openlrm", "가구");

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
