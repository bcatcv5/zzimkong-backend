package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.controller.dto.auth.SignInResponse;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.repository.UserRepository;
import com.boostcamp.zzimkong.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OauthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SignInResponse sign(User user) {
        user = findUser(user);
        String accessToken = jwtTokenProvider.createToken(user.getId().toString());
        return new SignInResponse(accessToken, user);
    }

    public User signUp(User user) {
        return userRepository.save(user);
    }

    public User findUser(User user) {
        return userRepository.findByOauthId(user.getOauthId())
                .orElseGet(() -> this.signUp(user));
    }
}
