package com.boostcamp.zzimkong.service.dto;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.domain.furniture.FurnitureUploadFile;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ImageFileSaveResponse {
    private Long id;

    private String uploadFileName;

    private String storeFileUrl;

    public static ImageFileSaveResponse from(FurnitureUploadFile file) {
        return new ImageFileSaveResponse(
                file.getId(),
                file.getUploadFileName(),
                file.getStoreFileUrl()
        );
    }
}
