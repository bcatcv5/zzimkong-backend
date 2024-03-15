package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.controller.dto.auth.SignInResponse;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.repository.UserRepository;
import com.boostcamp.zzimkong.support.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Transactional
@TestInstance(PER_CLASS)
public class OauthServiceTest {
    @Autowired
    private OauthService oauthService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private User dummyUser;

    @BeforeAll
    void setUp() {
        dummyUser = User.dummyUser();
    }

    @Test
    @DisplayName("유저 정보와 access token을 반환하며, 새로운 유저인 경우엔 유저 정보를 저장을 하고 반환한다.")
    void sign() {
        assertThat(userRepository.findAll().stream().count())
                .isEqualTo(0);

        SignInResponse signInResponse = oauthService.sign(dummyUser);
        assertThat(userRepository.findByOauthId(dummyUser.getOauthId()).orElseThrow())
                .isEqualTo(signInResponse.getUserInfo());
        Assertions.assertDoesNotThrow(() -> jwtTokenProvider.validateAbleToken(signInResponse.getAccessToken()));

        // 중복 저장 체크
        oauthService.sign(dummyUser);
        oauthService.sign(dummyUser);

        assertThat(userRepository.findAll().stream().count())
                .isEqualTo(1);
    }
}
