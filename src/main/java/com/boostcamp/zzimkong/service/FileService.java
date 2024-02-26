package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.space.ModelResult;
import com.boostcamp.zzimkong.domain.space.UploadFile;
import com.boostcamp.zzimkong.repository.SpaceRepository;
import com.boostcamp.zzimkong.repository.UserRepository;
import com.boostcamp.zzimkong.repository.modelresult.SpaceResultRepository;
import com.boostcamp.zzimkong.service.dto.VideoFileSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final SpaceResultRepository spaceResultRepository;

    public VideoFileSaveResponse save(Long userId, String uploadFileName, String storeFileUrl) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());
        UploadFile uploadFile = new UploadFile(findUser, storeFileUrl, uploadFileName);

        spaceResultRepository.save(ModelResult.from(findUser));
        UploadFile saveUploadFile = spaceRepository.save(uploadFile);
        return VideoFileSaveResponse.from(saveUploadFile);
    }
}
