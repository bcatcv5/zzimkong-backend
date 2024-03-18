package com.boostcamp.zzimkong.config;

import com.boostcamp.zzimkong.controller.auth.SignInterceptor;
import com.boostcamp.zzimkong.controller.auth.AuthenticationPrincipalArgumentResolver;
import com.boostcamp.zzimkong.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor(jwtTokenProvider))
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/oauth/google")
                .excludePathPatterns("/api/interiorManager/items/gallery/**")
                .excludePathPatterns("/api/file/shared/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver(jwtTokenProvider));
    }
}
