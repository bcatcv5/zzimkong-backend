package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.domain.FileType;
import com.boostcamp.zzimkong.domain.ObjectType;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import com.boostcamp.zzimkong.exception.InvalidObjectAccessException;
import com.boostcamp.zzimkong.exception.NoSuchFileUrlException;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
import com.boostcamp.zzimkong.repository.modelresult.SpaceResultRepository;
import com.boostcamp.zzimkong.support.SignedUrlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileAccessService {
    private final FurnitureResultRepository furnitureResultRepository;
    private final SpaceResultRepository spaceResultRepository;
    private final SignedUrlGenerator signedUrlGenerator;

    public RedirectView redirectToSignedUrl(Long userId, ObjectType objectType, Long objectId, FileType fileType) throws IOException {
        String unSignedUrl = "";

        switch (objectType){
            case SPACE -> {
                SpaceModelResult object = spaceResultRepository.findSpaceModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                        .orElseThrow(InvalidObjectAccessException::new);
                unSignedUrl = fileType.equals(FileType.INFERENCE) ? object.getStoreFileUrl() : object.getThumbnailFileUrl();
            }
            case FURNITURE -> {
                FurnitureModelResult object = furnitureResultRepository.findFurnitureModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                        .orElseThrow(InvalidObjectAccessException::new);
                unSignedUrl = fileType.equals(FileType.INFERENCE) ? object.getStoreFileUrl() : object.getThumbnailFileUrl();
            }
        }

        if (unSignedUrl.equals("")) throw new NoSuchFileUrlException();


        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(signedUrlGenerator.generateSignedUrl(unSignedUrl));
        return redirectView;
    }

    public RedirectView redirectToSignedUrl(ObjectType objectType, Long objectId, FileType fileType) throws IOException {
        String unSignedUrl = "";

        switch (objectType){
            case SPACE -> {
                SpaceModelResult object = spaceResultRepository.findByIdAndSharedIsAndDeletedIs(objectId,true,false)
                        .orElseThrow(InvalidObjectAccessException::new);
                unSignedUrl = fileType.equals(FileType.INFERENCE) ? object.getStoreFileUrl() : object.getThumbnailFileUrl();
            }
            case FURNITURE -> {
                FurnitureModelResult object = furnitureResultRepository.findByIdAndSharedIsAndDeletedIs(objectId, true, false)
                        .orElseThrow(InvalidObjectAccessException::new);
                unSignedUrl = fileType.equals(FileType.INFERENCE) ? object.getStoreFileUrl() : object.getThumbnailFileUrl();
            }
        }

        if (unSignedUrl.equals("")) throw new NoSuchFileUrlException();
        
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(signedUrlGenerator.generateSignedUrl(unSignedUrl));
        return redirectView;
    }
}
