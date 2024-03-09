package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.support.UuidHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileConverter {
    public static RawFileData convertVideoFile(final MultipartFile file, final UuidHolder uuidHolder, final String title) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            return new RawFileData(
                    title,
                    uuidHolder.random(),
                    file.getContentType(),
                    FileExtension.getExtensionFromFileName(title),
                    file.getInputStream()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<RawFileData> convertImageFiles(final List<MultipartFile> files, final UuidHolder uuidHolder, final String title) {
        if (CollectionUtils.isEmpty(files)) {
            return List.of();
        }

        return files.stream()
                .map(
                        file -> {
                            try {
                                return new RawFileData(
                                        title,
                                        uuidHolder.random(),
                                        file.getContentType(),
                                        FileExtension.getExtensionFromFileName(title),
                                        file.getInputStream()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).collect(Collectors.toList());
    }
}
