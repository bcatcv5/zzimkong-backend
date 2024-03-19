package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.config.JpaAuditingConfig;
import com.boostcamp.zzimkong.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = User.dummyUser();
        userRepository.save(dummyUser);
    }

    @Test
    @DisplayName("OauthId가 일치하는 유저를 반환한다.")
    void returnUserByOauthId() {
        assertThat(userRepository.findByOauthId(dummyUser.getOauthId()))
                .isEqualTo(Optional.ofNullable(dummyUser));

        assertThat(userRepository.findByOauthId("noExistOauthId"))
                .isEqualTo(Optional.empty());
    }
}
