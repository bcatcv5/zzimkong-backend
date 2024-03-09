package com.boostcamp.zzimkong.domain.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

@Getter
@AllArgsConstructor
public class RawFileData {
    private String uploadFileName;
    private String storeFileName;
    private String contentType;
    private String extension;
    private InputStream content;
}
