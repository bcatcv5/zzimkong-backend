package com.boostcamp.zzimkong.controller.dto;

import com.boostcamp.zzimkong.utils.validator.VideoExistsConstraint;
import com.boostcamp.zzimkong.utils.validator.VideoExtensionContraint;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoUploadRequest {

    @VideoExistsConstraint
    @VideoExtensionContraint
    private MultipartFile file;

    private Long id;
}
