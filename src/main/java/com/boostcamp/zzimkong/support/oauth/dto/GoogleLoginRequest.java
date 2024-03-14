package com.boostcamp.zzimkong.support.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GoogleLoginRequest {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;

    public static GoogleLoginRequest from(String code){
        return new GoogleLoginRequest(
                code,
                GoogleLoginPayload.client_id,
                GoogleLoginPayload.client_secret,
                GoogleLoginPayload.redirect_uri,
                GoogleLoginPayload.grant_type
        );
    }
}
