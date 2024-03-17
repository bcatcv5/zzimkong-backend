package com.boostcamp.zzimkong.support;

import com.boostcamp.zzimkong.exception.TokenInvalidExpiredException;
import com.boostcamp.zzimkong.exception.TokenInvalidFormException;
import com.boostcamp.zzimkong.exception.TokenInvalidSecretKeyException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.token.secret-key}") final String secretKey,
                            @Value("${jwt.token.expire-length}") final long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(String userId) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .setSubject(userId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(final String token) {
        return tokenToJws(token)
                .getBody()
                .getSubject();
    }

    public void validateAbleToken(final String token) {
        try {
            final Jws<Claims> claims = tokenToJws(token);
            validateExpiredToken(claims);
        } catch (final JwtException | InvalidCsrfTokenException e) {
            throw new TokenInvalidSecretKeyException(token);
        }
    }

    private Jws<Claims> tokenToJws(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (final IllegalArgumentException | MalformedJwtException e) {
            throw new TokenInvalidFormException();
        } catch (final SignatureException e) {
            throw new TokenInvalidSecretKeyException(token);
        } catch (final ExpiredJwtException e) {
            throw new TokenInvalidExpiredException();
        }
    }

    private void validateExpiredToken(final Jws<Claims> claims) {
        if (claims.getBody().getExpiration().before(new Date())) {
            throw new TokenInvalidExpiredException();
        }
    }
}
