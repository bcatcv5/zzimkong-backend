package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.domain.furniture.FurnitureUploadFile;
import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import com.boostcamp.zzimkong.domain.space.SpaceUploadFile;
import com.boostcamp.zzimkong.exception.NoSuchMemberException;
import com.boostcamp.zzimkong.repository.FurnitureRepository;
import com.boostcamp.zzimkong.repository.SpaceRepository;
import com.boostcamp.zzimkong.repository.UserRepository;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
import com.boostcamp.zzimkong.repository.modelresult.SpaceResultRepository;
import com.boostcamp.zzimkong.service.dto.ImageFileSaveResponse;
import com.boostcamp.zzimkong.service.dto.ImageFileSaveResponses;
import com.boostcamp.zzimkong.service.dto.VideoFileSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private static final int START = 0;

    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceResultRepository spaceResultRepository;
    private final FurnitureRepository furnitureRepository;
    private final FurnitureResultRepository furnitureResultRepository;

    public VideoFileSaveResponse save(Long userId, String uploadFileName, String storeFileUrl) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchMemberException(userId));
        SpaceUploadFile spaceUploadFile = new SpaceUploadFile(findUser, storeFileUrl, uploadFileName);

        spaceResultRepository.save(SpaceModelResult.of(findUser, uploadFileName));
        SpaceUploadFile saveSpaceUploadFile = spaceRepository.save(spaceUploadFile);
        return VideoFileSaveResponse.from(saveSpaceUploadFile);
    }

    public ImageFileSaveResponses save(Long userId, String uploadFileName, List<String> imageUploadUrls) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchMemberException(userId));

        List<FurnitureUploadFile> furnitureUploadFiles = IntStream.range(START, imageUploadUrls.size())
                .mapToObj(idx -> new FurnitureUploadFile(
                        findUser,
                        uploadFileName,
                        imageUploadUrls.get(idx))
                ).collect(Collectors.toList());

        IntStream.range(START, furnitureUploadFiles.size())
                        .forEach(idx -> {
                            furnitureRepository.save(furnitureUploadFiles.get(idx));
                            furnitureResultRepository.save(FurnitureModelResult.of(findUser, uploadFileName));
                        });

        List<ImageFileSaveResponse> imageFileSaveResponses = furnitureUploadFiles.stream()
                .map(file -> ImageFileSaveResponse.from(file))
                .collect(Collectors.toList());

        return ImageFileSaveResponses.from(imageFileSaveResponses);
    }
}
