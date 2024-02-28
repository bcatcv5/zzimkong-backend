package com.boostcamp.zzimkong.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VideoExtensionConstraintValidator.class)
public @interface VideoExtensionContraint {
    String message() default "파일은 mp4, mov만 허용합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}