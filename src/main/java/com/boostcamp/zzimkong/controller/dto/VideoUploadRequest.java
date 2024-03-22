package com.boostcamp.zzimkong.controller.dto;

import com.boostcamp.zzimkong.service.dto.VideoUploadRequestDto;
import com.boostcamp.zzimkong.utils.validator.VideoExistsConstraint;
import com.boostcamp.zzimkong.utils.validator.VideoExtensionContraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoUploadRequest {

    @VideoExistsConstraint
    @VideoExtensionContraint
    private MultipartFile file;

    @NotBlank
    private String title;

    public VideoUploadRequestDto toServiceDto(String videoUploadUrl, Long messageId) {
        return new VideoUploadRequestDto(
                title,
                videoUploadUrl,
                messageId
        );
    }
}
