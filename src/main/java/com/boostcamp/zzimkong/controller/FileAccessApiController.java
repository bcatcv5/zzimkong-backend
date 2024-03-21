package com.boostcamp.zzimkong.controller;

import com.boostcamp.zzimkong.controller.auth.AuthenticationPrincipal;
import com.boostcamp.zzimkong.controller.dto.auth.SignUserRequest;
import com.boostcamp.zzimkong.controller.dto.interior.FileAccessRequestQueryParam;
import com.boostcamp.zzimkong.service.FileAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileAccessApiController {
    private final FileAccessService fileAccessService;

    @GetMapping(value = "/me")
    public RedirectView file(
            @ModelAttribute FileAccessRequestQueryParam fileAccessRequestQueryParam,
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) throws IOException {
        // TODO: FileAccessRequestQueryParam 체크
        return fileAccessService.redirectToSignedUrl(
                signUserRequest.getId(),
                fileAccessRequestQueryParam.getObjectType(),
                fileAccessRequestQueryParam.getObjectId(),
                fileAccessRequestQueryParam.getFileType()
        );
    }

    @GetMapping(value = "/shared")
    public RedirectView sharedFile(
            @ModelAttribute FileAccessRequestQueryParam fileAccessRequestQueryParam
    ) throws IOException {
        return fileAccessService.redirectToSignedUrl(
                fileAccessRequestQueryParam.getObjectType(),
                fileAccessRequestQueryParam.getObjectId(),
                fileAccessRequestQueryParam.getFileType()
        );
    }
}
