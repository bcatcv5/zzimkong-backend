package com.boostcamp.zzimkong.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageExistsConstraintValidator implements ConstraintValidator<ImageExistsConstraint, List<MultipartFile>> {
    @Override
    public boolean isValid(final List<MultipartFile> files, final ConstraintValidatorContext context) {
        return files.stream()
                .anyMatch(
                        file -> file != null && !file.isEmpty()
                );
    }
}