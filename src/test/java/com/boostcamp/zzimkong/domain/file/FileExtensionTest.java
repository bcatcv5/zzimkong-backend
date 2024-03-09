package com.boostcamp.zzimkong.domain.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FileExtensionTest {

    @ParameterizedTest
    @ValueSource(strings = {
                    "file.hwp",
                    "file.doc",
                    "file.gif",
                    "file.txt",
                    "file.docx",
                    "file.hwpx",
                    "file.pdf",
                    "file.ppt",
                    "file.pptx"
            })
    @DisplayName("지원하지 않은 업로드 포멧은 예외가 발생한다.")
    void throwException_invalidFileFormat(String unExpectedFormatFile) {
        assertThat(FileExtension.isValidExtension(unExpectedFormatFile))
                .isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "file.jpg:.jpg",
            "file.png:.png",
            "file.jpeg:.jpeg",
            "file.mp4:.mp4",
            "file.mov:.mov"
    }, delimiter = ':')
    @DisplayName("지원하는 업로드 포멧의 확장자를 반환한다.")
    void getExtensionFromFileName(String fileName, String ext) {
        assertThat(FileExtension.getExtensionFromFileName(fileName))
                .isEqualTo(ext);
    }
}
