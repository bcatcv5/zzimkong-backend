package com.boostcamp.zzimkong.support.oauth;

import com.boostcamp.zzimkong.exception.OauthHttpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class OauthRequesterTest {
    // TODO: 구글 Oauth Credential Code가 올바른 경우

    @Test
    @DisplayName("구글 Oauth Credential Code가 올바르지 않은 경우 에러를 발생한다.")
    void throwException_invalidCode() {
        assertThatThrownBy(() -> OauthRequester.googleOauthRequest("fakeCode"))
                .isInstanceOf(OauthHttpException.class);
    }
}
