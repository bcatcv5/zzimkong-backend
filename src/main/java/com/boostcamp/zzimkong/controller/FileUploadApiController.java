package com.boostcamp.zzimkong.controller;

import com.boostcamp.zzimkong.controller.dto.ImageUploadRequest;
import com.boostcamp.zzimkong.controller.dto.VideoUploadRequest;
import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.service.FileService;
import com.boostcamp.zzimkong.service.dto.ImageFileSaveResponses;
import com.boostcamp.zzimkong.service.dto.VideoFileSaveResponse;
import com.boostcamp.zzimkong.support.UuidHolder;
import com.boostcamp.zzimkong.support.file.FileConverter;
import com.boostcamp.zzimkong.support.file.GCPFileUploader;
import com.boostcamp.zzimkong.support.MessageConsumer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final MessageConsumer messageConsumer;

    @PostMapping(value = "/video", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VideoFileSaveResponse> uploadImage(
            @ModelAttribute @Valid VideoUploadRequest videoUploadRequest
    ) {
        RawFileData rawFileData = FileConverter.convertVideoFile(
                videoUploadRequest.getFile(),
                uuidHolder
        );
        String videoUploadUrl = gcpFileUploader.uploadVideo(rawFileData);
        messageConsumer.sendSpaceMessage(videoUploadUrl, "공간");

        VideoFileSaveResponse videoFileSaveResponse =
                fileService.save(videoUploadRequest.getId(), videoUploadRequest.getTitle(), videoUploadUrl);
        return ResponseEntity
                .created(URI.create("/api/video/" + videoFileSaveResponse.getId()))
                .body(videoFileSaveResponse);
    }

    @PostMapping(value = "/images", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageFileSaveResponses> uploadImage(
            @ModelAttribute @Valid ImageUploadRequest imageUploadRequest
    ) {
        List<RawFileData> rawFileDatas = FileConverter.convertImageFiles(
                imageUploadRequest.getFiles(),
                uuidHolder
        );
        List<String> imageUploadUrls = gcpFileUploader.uploadImages(rawFileDatas);
        messageConsumer.sendSpaceMessage(imageUploadUrls.get(0), "가구");

        ImageFileSaveResponses imageFileSaveResponses =
                fileService.save(imageUploadRequest.getId(), imageUploadRequest.getTitle(), imageUploadUrls);

        return ResponseEntity
                .ok(imageFileSaveResponses);
    }
}
