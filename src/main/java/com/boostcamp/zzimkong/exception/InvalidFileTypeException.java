package com.boostcamp.zzimkong.exception;

import com.boostcamp.zzimkong.domain.file.FileExtension;
import org.springframework.http.HttpStatus;

public class InvalidFileTypeException extends ZzimkongException {
    public InvalidFileTypeException(final String extension) {
        super(
                String.format("지원하지 않는 파일 형식입니다. extension={%s}", extension),
                "지원하지 않는 파일 형식입니다.",
                HttpStatus.BAD_REQUEST,
                "4001"
        );
    }
}
