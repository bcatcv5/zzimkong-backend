package com.boostcamp.zzimkong.service;

import com.boostcamp.zzimkong.controller.dto.interior.GalleryObjectListResponse;
import com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse;
import com.boostcamp.zzimkong.domain.ObjectType;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import com.boostcamp.zzimkong.exception.InvalidObjectAccessException;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
import com.boostcamp.zzimkong.repository.modelresult.SpaceResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InteriorService {
    private final SpaceResultRepository spaceResultRepository;
    private final FurnitureResultRepository furnitureResultRepository;

    public List<ObjectListResponse> spaceList(Long id) {
        return spaceResultRepository.findObjectListResponseByUserId(id);
    }

    public List<ObjectListResponse> furnitureList(Long id) {
        return furnitureResultRepository.findObjectListResponseByUserId(id);
    }

    public List<GalleryObjectListResponse> gallerySpaceList() {
        return spaceResultRepository.findGalleryObjectListResponseByUserId();
    }

    public List<GalleryObjectListResponse> galleryFurnitureList() {
        return furnitureResultRepository.findGalleryObjectListResponseByUserId();
    }

    public void updateShared(Long userId, Long objectId, ObjectType objectType){
        if (objectType.equals(ObjectType.SPACE)) {
            SpaceModelResult result = spaceResultRepository.findSpaceModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setShared(!result.isShared());
        } else {
            FurnitureModelResult result = furnitureResultRepository.findFurnitureModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setShared(!result.isShared());
        }
    }

    public void updateRating(Long userId, Long objectId, int rating, ObjectType objectType){
        if (objectType.equals(ObjectType.SPACE)) {
            SpaceModelResult result = spaceResultRepository.findSpaceModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setRating(rating);
        } else {
            FurnitureModelResult result = furnitureResultRepository.findFurnitureModelResultByUserIdAndIdAndDeletedIs(userId, objectId, false)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setRating(rating);
        }
    }


    public void delete(Long userId, Long objectId, ObjectType objectType){
        if (objectType.equals(ObjectType.SPACE)) {
            SpaceModelResult result = spaceResultRepository.findSpaceModelResultByUserIdAndId(userId, objectId)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setDeleted(true);
        } else {
            FurnitureModelResult result = furnitureResultRepository.findFurnitureModelResultByUserIdAndId(userId, objectId)
                    .orElseThrow(InvalidObjectAccessException::new);
            result.setDeleted(true);
        }
    }
}
