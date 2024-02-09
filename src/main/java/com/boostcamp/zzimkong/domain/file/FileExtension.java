package com.boostcamp.zzimkong.domain.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FileExtension {

    // image
    JPG(".jpg"),
    PNG(".png"),
    JPEG(".jpeg"),

    // video
    MP4(".mp4"),
    MOV(".mov");

    private final String value;

    public static String getExtensionFromFileName(final String uploadFileName) {
        String fileExtension = extractFileExtension(uploadFileName);

        FileExtension findFileExtension = Arrays.stream(values())
                .filter(extension -> extension.value.equals(fileExtension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(fileExtension));

        return findFileExtension.getValue();
    }

    private static String extractFileExtension(String uploadFileName) {
        return uploadFileName.substring(uploadFileName.lastIndexOf("."));
    }

    public static boolean isValidExtension(final String fileName) {
        final String fileExtension = extractFileExtension(fileName);

        return Arrays.stream(values())
                .anyMatch(extension -> extension.value.equals(fileExtension));
    }
}
