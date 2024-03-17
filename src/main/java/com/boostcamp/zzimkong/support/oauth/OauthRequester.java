package com.boostcamp.zzimkong.support.oauth;

import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.exception.OauthHttpException;
import com.boostcamp.zzimkong.support.oauth.dto.GoogleLoginUserInfo;
import com.boostcamp.zzimkong.support.oauth.dto.GoogleLoginRequest;
import com.boostcamp.zzimkong.support.oauth.dto.GoogleLoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

public class OauthRequester {
    public static User googleOauthRequest (final String code) throws JsonProcessingException {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://oauth2.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        GoogleLoginRequest googleLoginRequest = GoogleLoginRequest.from(code);
        GoogleLoginResponse googleLoginResponse = webClient
                .post()
                .uri("/token")
                .bodyValue(googleLoginRequest)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, response ->
                        Mono.error(new OauthHttpException("google", HttpStatus.BAD_REQUEST)
                        )
                )
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response ->
                        Mono.error(new OauthHttpException("google", HttpStatus.INTERNAL_SERVER_ERROR)
                        )
                )
                .bodyToMono(GoogleLoginResponse.class)
                .block();

        assert googleLoginResponse != null;

        final String payloadJWT = googleLoginResponse.getId_token().split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        final String payload = new String(decoder.decode(payloadJWT));

        ObjectMapper objectMapper = new
        ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GoogleLoginUserInfo googleLoginUserInfo = objectMapper.readValue(payload, GoogleLoginUserInfo.class);

        return googleLoginUserInfo.toUser();
    }
}
