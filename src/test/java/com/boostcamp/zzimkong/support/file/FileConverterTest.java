package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.support.UuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileConverterTest {

    @Autowired
    private UuidHolder uuidHolder;

    @Test
    @DisplayName("빈 파일이 업로드되면 null을 반환한다.")
    void convertEmptyFile() throws IOException {
        // given
        String originalFileName = "test.mp4";
        FileInputStream fileInputStream = null;
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test_mock",
                originalFileName,
                "mp4",
                fileInputStream
        );

        // then
        assertThat(FileConverter.convertVideoFile(mockMultipartFile, uuidHolder))
                .isNull();
    }

    @Test
    @DisplayName("비어있지 않은 영상이 업로드되면 컨버팅한다.")
    void convertVideoFile() throws IOException {
        // given
        String originalFileName = "test.mp4";
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/videos/" + originalFileName);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test_mock",
                originalFileName,
                "mp4",
                fileInputStream
        );

        // then
        assertThat(FileConverter.convertVideoFile(mockMultipartFile, uuidHolder))
                .extracting("uploadFileName", "storeFileName", "contentType", "extension")
                .containsExactly(
                        originalFileName,
                        "1234abcd1234abcd",
                        mockMultipartFile.getContentType(),
                        ".mp4"
                );
    }
}