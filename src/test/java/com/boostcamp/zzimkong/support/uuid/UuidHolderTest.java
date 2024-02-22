package com.boostcamp.zzimkong.support.uuid;

import com.boostcamp.zzimkong.support.UuidHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UuidHolderTest {

    @Autowired
    private UuidHolder uuidHolder;

    @Test
    @DisplayName("테스트용 uuid holder는 랜덤 값이 아닌 값을 반환합니다.")
    void getRandom() {

        // given
        String actual = uuidHolder.random();
        String expected = "1234abcd1234abcd";

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
