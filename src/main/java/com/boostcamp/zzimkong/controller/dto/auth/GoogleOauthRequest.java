package com.boostcamp.zzimkong.controller.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleOauthRequest {
    @NotNull
    private String code;
}
