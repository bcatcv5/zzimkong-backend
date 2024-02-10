package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.support.UuidHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileConverter {
    public static RawFileData convertVideoFile(final MultipartFile file, final UuidHolder uuidHolder) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        final String filename = file.getOriginalFilename();

        try {
            return new RawFileData(
                    filename,
                    uuidHolder.random(),
                    file.getContentType(),
                    FileExtension.getExtensionFromFileName(filename),
                    file.getInputStream()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
