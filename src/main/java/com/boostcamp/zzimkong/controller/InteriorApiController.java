package com.boostcamp.zzimkong.controller;

import com.boostcamp.zzimkong.controller.auth.AuthenticationPrincipal;
import com.boostcamp.zzimkong.controller.dto.auth.SignUserRequest;
import com.boostcamp.zzimkong.controller.dto.interior.GalleryObjectListResponse;
import com.boostcamp.zzimkong.controller.dto.interior.InteriorObjectEditRequestParam;
import com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse;
import com.boostcamp.zzimkong.service.InteriorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interiorManager")
public class InteriorApiController {
    private final InteriorService interiorService;

    @GetMapping(value = "/items/space")
    public ResponseEntity<List<ObjectListResponse>> space(
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) {
        return ResponseEntity.ok(
                interiorService.spaceList(signUserRequest.getId())
        );
    }

    @GetMapping(value = "/items/furniture")
    public ResponseEntity<List<ObjectListResponse>> furniture(
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) {
        return ResponseEntity.ok(
                interiorService.furnitureList(signUserRequest.getId())
        );
    }

    @GetMapping(value = "/items/gallery/space")
    public ResponseEntity<List<GalleryObjectListResponse>> gallerySpace(
    ) {
        return ResponseEntity.ok(
                interiorService.gallerySpaceList()
        );
    }

    @GetMapping(value = "/items/gallery/furniture")
    public ResponseEntity<List<GalleryObjectListResponse>> galleryFurniture(
    ) {
        return ResponseEntity.ok(
                interiorService.galleryFurnitureList()
        );
    }

    @PostMapping(value = "/rating")
    public ResponseEntity<Void> rating(
            @ModelAttribute InteriorObjectEditRequestParam interiorObjectEditRequestParam,
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) {
        interiorService.updateRating(signUserRequest.getId(),
                interiorObjectEditRequestParam.getObjectId(),
                interiorObjectEditRequestParam.getRating(),
                interiorObjectEditRequestParam.getObjectType()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/share")
    public ResponseEntity<Void> share(
            @ModelAttribute InteriorObjectEditRequestParam interiorObjectEditRequestParam,
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) {
        interiorService.updateShared(signUserRequest.getId(),
                interiorObjectEditRequestParam.getObjectId(),
                interiorObjectEditRequestParam.getObjectType()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> delete(
            @ModelAttribute InteriorObjectEditRequestParam interiorObjectEditRequestParam,
            @AuthenticationPrincipal SignUserRequest signUserRequest
    ) {
        interiorService.delete(signUserRequest.getId(),
                interiorObjectEditRequestParam.getObjectId(),
                interiorObjectEditRequestParam.getObjectType()
        );
        return ResponseEntity.noContent().build();
    }
}
