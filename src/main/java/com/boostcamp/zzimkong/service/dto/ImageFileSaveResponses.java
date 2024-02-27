package com.boostcamp.zzimkong.service.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ImageFileSaveResponses {
    private List<ImageFileSaveResponse> imageFileSaveResponses;

    public static ImageFileSaveResponses from(List<ImageFileSaveResponse> imageFileSaveResponses) {
        return new ImageFileSaveResponses(imageFileSaveResponses);
    }
}
