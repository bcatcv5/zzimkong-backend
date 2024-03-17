package com.boostcamp.zzimkong.controller.dto.auth;

import com.boostcamp.zzimkong.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignInResponse {
    private String accessToken;
    private User userInfo;

    public SignInResponse(String accessToken, User user){
        this.accessToken = accessToken;
        this.userInfo = user;
    }
}
