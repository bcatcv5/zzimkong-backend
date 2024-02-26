package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.config.JpaAuditingConfig;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.space.UploadFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class SpaceRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    private UploadFile spaceUploadFile;
    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = new User("dummy");
        spaceUploadFile = new UploadFile(dummyUser, "storeName", "uploadName");

        userRepository.save(dummyUser);
    }

    @Test
    @DisplayName("파일 경로를 저장한다.")
    void save() {
        // given
        UploadFile saveUploadFile = spaceRepository.save(spaceUploadFile);
      
        // then
        assertAll(
                () -> assertThat(saveUploadFile.getId()).isNotNull(),
                () -> assertThat(saveUploadFile).isEqualTo(spaceUploadFile)
        );
    }
}