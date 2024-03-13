package com.boostcamp.zzimkong.controller.dto;

import com.boostcamp.zzimkong.service.dto.ImageUploadRequestDto;
import com.boostcamp.zzimkong.utils.validator.ImageExistsConstraint;
import com.boostcamp.zzimkong.utils.validator.ImageExtensionConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadRequest {

    @ImageExtensionConstraint
    @ImageExistsConstraint
    private List<MultipartFile> files;

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    public ImageUploadRequestDto toServiceDto(List<String> imageUploadUrls, Long messageId) {
        return new ImageUploadRequestDto(
                id,
                title,
                imageUploadUrls,
                messageId
        );
    }
}
