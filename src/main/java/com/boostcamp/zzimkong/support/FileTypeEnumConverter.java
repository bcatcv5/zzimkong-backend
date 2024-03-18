package com.boostcamp.zzimkong.support;

import com.boostcamp.zzimkong.domain.FileType;
import org.springframework.core.convert.converter.Converter;

public class FileTypeEnumConverter implements Converter<String, FileType> {
    @Override
    public FileType convert(String source) {
        String value = (source.equals("3D")) ? "INFERENCE" : "THUMBNAIL";
        try {
            return FileType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
