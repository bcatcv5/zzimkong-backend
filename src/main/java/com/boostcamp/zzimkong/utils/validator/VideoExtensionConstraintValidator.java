package com.boostcamp.zzimkong.utils.validator;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class VideoExtensionConstraintValidator implements ConstraintValidator<VideoExtensionContraint, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        return FileExtension.isValidExtension(file.getOriginalFilename());
    }
}
