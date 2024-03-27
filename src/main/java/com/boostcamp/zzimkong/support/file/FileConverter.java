package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.support.UuidHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.boostcamp.zzimkong.utils.ZzimkongConstant.SPACE_URI;
import static com.boostcamp.zzimkong.utils.ZzimkongConstant.STORAGE_URL;

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

    public static List<RawFileData> convertImageFiles(final List<MultipartFile> files, final UuidHolder uuidHolder) {
        if (CollectionUtils.isEmpty(files)) {
            return List.of();
        }

        return files.stream()
                .map(
                        file -> {
                            try {
                                final String filename = file.getOriginalFilename();

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
                ).collect(Collectors.toList());
    }

    public static String createFileStoreUrl(String fileName, String bucket) {
        return String.format("%s/%s/%s%s", STORAGE_URL, bucket, SPACE_URI, fileName);
    }
}
