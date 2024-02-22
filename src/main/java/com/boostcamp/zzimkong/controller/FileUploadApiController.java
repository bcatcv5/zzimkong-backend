package com.boostcamp.zzimkong.controller;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.support.UuidHolder;
import com.boostcamp.zzimkong.support.file.FileConverter;
import com.boostcamp.zzimkong.support.file.GCPFileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadApiController {

    private final GCPFileUploader gcpFileUploader;
    private final UuidHolder uuidHolder;

    @PostMapping(value = "/video", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestPart(name = "file") MultipartFile file) {
        RawFileData rawFileData = FileConverter.convertVideoFile(file, uuidHolder);
        String videoUploadLink = gcpFileUploader.uploadFile(rawFileData);

        return ResponseEntity.ok(videoUploadLink);
    }
}
