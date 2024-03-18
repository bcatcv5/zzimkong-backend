package com.boostcamp.zzimkong.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUploadRequestDto {

    private String uploadFileName;
    private List<String> storeFileUrl;
    private Long messageId;
}
