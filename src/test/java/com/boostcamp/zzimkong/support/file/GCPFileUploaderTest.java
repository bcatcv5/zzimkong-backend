package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
class GCPFileUploaderTest {

    @MockBean
    private GCPFileUploader fakeGcpFileUploader;

    @Test
    @DisplayName("빈 파일이 업로드되면 예외가 발생한다.")
    void throwException_emptyFile() {
        // given
        RawFileData emptyRawFileData = null;

        // then
        doThrow(new IllegalArgumentException()).when(fakeGcpFileUploader)
                .uploadVideo(emptyRawFileData);

        assertThatThrownBy(() -> fakeGcpFileUploader.uploadVideo(emptyRawFileData))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("잘못된 bucket 이름이면 예외가 발생한다.")
    void throwException_invalidBucket() throws IOException {
        // given
        String originalFileName = "test.mp4";
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/videos/" + originalFileName);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test_mock",
                originalFileName,
                "mp4",
                fileInputStream
        );
        RawFileData dummyData = new RawFileData(
                "test",
                "test",
                "mp4",
                ".mp4",
                mockMultipartFile.getInputStream()
        );

        // when
        doThrow(new RuntimeException()).when(fakeGcpFileUploader)
                .uploadVideo(dummyData);

        // then
        assertThatThrownBy(() -> fakeGcpFileUploader.uploadVideo(dummyData))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("파일을 업로드한다.")
    void uploadFile() throws IOException {
        String originalFileName = "test.mp4";
        String URL = "https://storage.googleapis.com/test/1234abcd1234abcd.mp4";

        FileInputStream fileInputStream = new FileInputStream("src/test/resources/videos/" + originalFileName);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test_mock",
                originalFileName,
                "mp4",
                fileInputStream
        );
        RawFileData dummyData = new RawFileData(
                mockMultipartFile.getOriginalFilename(),
                "1234abcd1234abcd",
                "video/mp4",
                ".mp4",
                mockMultipartFile.getInputStream()
        );

        // when
        when(fakeGcpFileUploader.uploadVideo(dummyData))
                .thenReturn(URL);

        // then
        assertThat(fakeGcpFileUploader.uploadVideo(dummyData))
                .isEqualTo(URL);
    }
}
