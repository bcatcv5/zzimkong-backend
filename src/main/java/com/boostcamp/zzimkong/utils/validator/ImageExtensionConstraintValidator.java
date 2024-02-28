package com.boostcamp.zzimkong.utils.validator;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageExtensionConstraintValidator implements ConstraintValidator<ImageExtensionConstraint, List<MultipartFile>> {
    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return true;
        }

        return files.stream()
                .anyMatch(
                        file -> FileExtension.isValidExtension(file.getOriginalFilename())
                );
    }
}
