package com.boostcamp.zzimkong.controller.auth;

import com.boostcamp.zzimkong.support.AuthorizationExtractor;
import com.boostcamp.zzimkong.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class SignInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public SignInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        final String token = AuthorizationExtractor.extract(request);
        jwtTokenProvider.validateAbleToken(token);
        return true;
    }
}
