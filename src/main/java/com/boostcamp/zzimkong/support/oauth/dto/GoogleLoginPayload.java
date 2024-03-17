package com.boostcamp.zzimkong.support.oauth.dto;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GoogleLoginPayload {
    public static String client_id;
    public static String client_secret;
    public static String redirect_uri;
    public static String grant_type;

    @Value("${oauth.google.payload.client_id}")
    private void setClient_id(String client_id) {
        GoogleLoginPayload.client_id = client_id;
    }

    @Value("${oauth.google.payload.client_secret}")
    private void setClient_secret(String client_secret) {
        GoogleLoginPayload.client_secret = client_secret;
    }

    @Value("${oauth.google.payload.redirect_uri}")
    private void setRedirect_uri(String redirect_uri) {
        GoogleLoginPayload.redirect_uri = redirect_uri;
    }

    @Value("${oauth.google.payload.grant_type}")
    private void setGrant_type(String grant_type) {
        GoogleLoginPayload.grant_type = grant_type;
    }
}
