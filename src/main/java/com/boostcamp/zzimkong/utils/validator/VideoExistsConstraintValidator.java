package com.boostcamp.zzimkong.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class VideoExistsConstraintValidator implements ConstraintValidator<VideoExistsConstraint, MultipartFile> {
    @Override
    public boolean isValid(final MultipartFile file, final ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
