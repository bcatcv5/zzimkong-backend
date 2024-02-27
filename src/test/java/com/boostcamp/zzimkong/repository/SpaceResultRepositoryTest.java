package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.config.JpaAuditingConfig;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.space.SpaceUploadFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class SpaceRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    private SpaceUploadFile spaceUploadFile;
    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = new User("dummy");
        spaceUploadFile = new SpaceUploadFile(dummyUser, "storeName", "uploadName");

        userRepository.save(dummyUser);
    }

    @Test
    @DisplayName("파일 경로를 저장한다.")
    void save() {
        // given
        SpaceUploadFile saveSpaceUploadFile = spaceRepository.save(spaceUploadFile);
      
        // then
        assertAll(
                () -> assertThat(saveSpaceUploadFile.getId()).isNotNull(),
                () -> assertThat(saveSpaceUploadFile).isEqualTo(spaceUploadFile)
        );
    }
}