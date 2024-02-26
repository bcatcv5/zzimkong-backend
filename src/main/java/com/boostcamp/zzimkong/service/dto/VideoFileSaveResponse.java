package com.boostcamp.zzimkong.service.dto;

import com.boostcamp.zzimkong.domain.space.UploadFile;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class VideoFileSaveResponse {

    private Long id;

    private String uploadFileName;

    private String storeFileUrl;

    public static VideoFileSaveResponse from(UploadFile file) {
        return new VideoFileSaveResponse(
                file.getId(),
                file.getUploadFileName(),
                file.getStoreFileUrl()
        );
    }
}
