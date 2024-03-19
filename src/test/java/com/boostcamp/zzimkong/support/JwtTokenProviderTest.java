package com.boostcamp.zzimkong.support;

import com.boostcamp.zzimkong.exception.TokenInvalidExpiredException;
import com.boostcamp.zzimkong.exception.TokenInvalidSecretKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String token;

    @BeforeEach
    void setUp() {
        token = jwtTokenProvider.createToken("21");
    }

    @Test
    @DisplayName("jwt token이 올바른 경우 id 값을 반환한다.")
    void returnJwtPayloadId() {

        // given
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA0MzQyNjYsImV4cCI6MTcxMDQzNDI2Niwic3ViIjoiMSJ9.5Xlgcm3y5fHLa4pUgNFhOizcG513y2XpqqHHEeZuY1U";
        String invalidSecretKeyToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoiYWRtaW4iLCJpYXQiOjE3MDkwMTE5NDIsImV4cCI6MTcxNzY1MTk0Mn0.GDqzeLFwWziLvFzRPNJ0AsJiy4l2UwzAy74Cg27wY5A";

        // then
        assertThatThrownBy(() -> jwtTokenProvider.getPayload(expiredToken))
                .isInstanceOf(TokenInvalidExpiredException.class);
        assertThatThrownBy(() -> jwtTokenProvider.getPayload(invalidSecretKeyToken))
                .isInstanceOf(TokenInvalidSecretKeyException.class);
        assertThat(jwtTokenProvider.getPayload(token))
                .isEqualTo("21");
    }
}
