package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.space.UploadFile;
import com.boostcamp.zzimkong.repository.SpaceRepository;
import com.boostcamp.zzimkong.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;

@SpringBootTest
@Transactional
@TestInstance(PER_CLASS)
class FileServiceTest {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpaceRepository spaceRepository;

    private User dummyUser;

    @BeforeAll
    void setUp() {
        dummyUser = new User("dummy");
        userRepository.save(dummyUser);
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 파일을 업로드하면, 예외가 발생한다.")
    void throwException_NoSuchUser() {
        // given
        Long unExistId = 2L;
        String uploadFileName = "uploadFileName";
        String storeFileUrl = "storeFileUrl";

        // then
        assertThatThrownBy(() -> fileService.save(unExistId, uploadFileName, storeFileUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일 업로드 메타 정보를 저장한다.")
    void saveFileAndFind() {
        // given
        Long userId = 1L;
        String expectedUploadFileName = "uploadFileName";
        String expectedStoreFileUrl = "storeFileUrl";

        // when
        Long saveFileId = fileService.save(
                userId,
                expectedUploadFileName,
                expectedStoreFileUrl
        ).getId();

        UploadFile findUploadFile = spaceRepository.findById(saveFileId)
                .orElseThrow();

        // then
        assertThat(findUploadFile).extracting("id", "storeFileUrl", "uploadFileName")
                .containsExactly(saveFileId, expectedStoreFileUrl, expectedUploadFileName);
    }
}