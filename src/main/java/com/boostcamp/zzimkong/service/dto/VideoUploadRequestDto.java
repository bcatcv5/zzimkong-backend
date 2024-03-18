package com.boostcamp.zzimkong.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoUploadRequestDto {

    private String uploadFileName;
    private String storeFileUrl;
    private Long messageId;
}
