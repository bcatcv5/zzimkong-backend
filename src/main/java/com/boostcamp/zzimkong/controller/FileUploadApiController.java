package com.boostcamp.zzimkong.controller;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.service.FileService;
import com.boostcamp.zzimkong.service.dto.ImageFileSaveResponses;
import com.boostcamp.zzimkong.service.dto.VideoFileSaveResponse;
import com.boostcamp.zzimkong.support.UuidHolder;
import com.boostcamp.zzimkong.support.file.FileConverter;
import com.boostcamp.zzimkong.support.file.GCPFileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadApiController {

    private final GCPFileUploader gcpFileUploader;
    private final UuidHolder uuidHolder;
    private final FileService fileService;

    @PostMapping(value = "/video", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoFileSaveResponse> uploadImage(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "id") Long id
    ) {
        RawFileData rawFileData = FileConverter.convertVideoFile(file, uuidHolder);
        String videoUploadUrl = gcpFileUploader.uploadVideo(rawFileData);

        VideoFileSaveResponse videoFileSaveResponse =
                fileService.save(id, rawFileData.getUploadFileName(), videoUploadUrl);
        return ResponseEntity
                .created(URI.create("/api/video/" + videoFileSaveResponse.getId()))
                .body(videoFileSaveResponse);
    }

    @PostMapping(value = "/images", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageFileSaveResponses> uploadImage(
            @RequestPart(name = "file") List<MultipartFile> files,
            @RequestParam(name = "id") Long id
    ) {
        List<RawFileData> rawFileDatas = FileConverter.convertImageFiles(files, uuidHolder);
        List<String> imageUploadUrls = gcpFileUploader.uploadImages(rawFileDatas);

        ImageFileSaveResponses imageFileSaveResponses =
                fileService.save(id, rawFileDatas, imageUploadUrls);

        return ResponseEntity
                .ok(imageFileSaveResponses);
    }
}
