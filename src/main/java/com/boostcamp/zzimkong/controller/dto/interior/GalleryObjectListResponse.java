package com.boostcamp.zzimkong.controller.dto.interior;

import com.boostcamp.zzimkong.domain.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GalleryObjectListResponse {
    private Long id;;
    private String userName;
    private String uploadFileName;
    private StatusCode statusCode;
    private String statusMessage;
    private String storeFileUrl;
    private String thumbnailFileUrl;
    private int rating;
    private boolean shared;
}
