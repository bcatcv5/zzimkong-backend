package com.boostcamp.zzimkong.domain;

import lombok.Getter;

@Getter
public enum FileType {
    INFERENCE("3d"),
    THUMBNAIL("thumbnail");

    private final String filetype;

    FileType(String filetype) {
        this.filetype = filetype;
    }
}
