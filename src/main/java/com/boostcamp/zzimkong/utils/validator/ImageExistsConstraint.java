package com.boostcamp.zzimkong.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageExistsConstraintValidator.class)
public @interface ImageExistsConstraint {
    String message() default "파일이 전송되지 않았습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}