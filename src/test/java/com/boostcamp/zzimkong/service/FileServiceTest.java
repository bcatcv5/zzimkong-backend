package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.space.SpaceUploadFile;
import com.boostcamp.zzimkong.exception.NoSuchMemberException;
import com.boostcamp.zzimkong.repository.SpaceRepository;
import com.boostcamp.zzimkong.repository.UserRepository;
import com.boostcamp.zzimkong.service.dto.VideoUploadRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

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
        dummyUser = User.dummyUser();
        userRepository.save(dummyUser);
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 파일을 업로드하면, 예외가 발생한다.")
    void throwException_NoSuchUser() {
        // given
        Long unExistId = 2L;

        // then
        assertThatThrownBy(() -> fileService.save(createDto(), unExistId))
                .isInstanceOf(NoSuchMemberException.class)
                .hasMessage("존재하지 않은 회원입니다. userId={%d}", unExistId);
    }

    @Test
    @DisplayName("파일 업로드 메타 정보를 저장한다.")
    void saveFileAndFind() {
        // given
        Long userId = 1L;
        String expectedUploadFileName = "uploadFileName";
        String expectedStoreFileUrl = "storeFileUrl";

        // when
        Long saveFileId = fileService.save(createDto(), userId)
                .getId();

        SpaceUploadFile findSpaceUploadFile = spaceRepository.findById(saveFileId)
                .orElseThrow();

        // then
        assertThat(findSpaceUploadFile).extracting("id", "storeFileUrl", "uploadFileName")
                .containsExactly(saveFileId, expectedStoreFileUrl, expectedUploadFileName);
    }

    private VideoUploadRequestDto createDto() {
        return new VideoUploadRequestDto("uploadFileName", "storeFileUrl", 1L);
    }
}