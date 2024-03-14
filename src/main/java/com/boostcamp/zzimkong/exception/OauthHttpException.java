package com.boostcamp.zzimkong.exception;

import org.springframework.http.HttpStatus;

public class OauthHttpException extends ZzimkongException{
    public OauthHttpException(final String provider, final HttpStatus httpStatus) {
        super(
                String.format("%s OAUTH BAD REQUEST", provider.toUpperCase()),
                String.format("%s OAUTH BAD REQUEST", provider.toUpperCase()),
                httpStatus,
                "4009"
        );
    }
}
