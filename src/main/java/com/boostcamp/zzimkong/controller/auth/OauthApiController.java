package com.boostcamp.zzimkong.controller.auth;

import com.boostcamp.zzimkong.controller.dto.auth.GoogleOauthRequest;
import com.boostcamp.zzimkong.controller.dto.auth.SignInResponse;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.service.OauthService;
import com.boostcamp.zzimkong.support.oauth.OauthRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/oauth")
public class OauthApiController {
    private final OauthService oauthService;

    @PostMapping(value = "/google")
    public ResponseEntity<SignInResponse> Sign(
            @RequestBody GoogleOauthRequest googleOauthRequest
            ) throws JsonProcessingException {
        User user = OauthRequester.googleOauthRequest(googleOauthRequest.getCode());
        SignInResponse signInResponse = oauthService.sign(user);
        return ResponseEntity
                .ok(signInResponse);
    }
}
