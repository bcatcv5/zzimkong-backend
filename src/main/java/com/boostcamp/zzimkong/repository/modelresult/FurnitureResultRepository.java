package com.boostcamp.zzimkong.repository.modelresult;

import com.boostcamp.zzimkong.controller.dto.interior.GalleryObjectListResponse;
import com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FurnitureResultRepository extends JpaRepository<FurnitureModelResult, Long> {
//    List<ObjectListResponse> findFurnitureModelResultByUserId(Long userId);

    Optional<FurnitureModelResult> findFurnitureModelResultByUserIdAndId(Long userId, Long id);

    Optional<FurnitureModelResult> findByIdAndSharedIsAndDeletedIs(Long id, boolean shared, boolean deleted);

    Optional<FurnitureModelResult> findFurnitureModelResultByUserIdAndIdAndDeletedIs(Long user_id, Long id, boolean deleted);

    @Query("""
            select
            new com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse(
                smr.id,
                smr.statusCode,
                smr.statusMessage,
                smr.storeFileUrl,
                smr.thumbnailFileUrl,
                smr.rating,
                smr.shared
            )
            from FurnitureModelResult smr
            join smr.user user
            where user.id = :userId and smr.deleted != true
            """
    )
    List<ObjectListResponse> findObjectListResponseByUserId(@Param("userId") Long userId);

    @Query("""
            select
            new com.boostcamp.zzimkong.controller.dto.interior.GalleryObjectListResponse(
                smr.id,
                user.name,
                smr.statusCode,
                smr.statusMessage,
                smr.storeFileUrl,
                smr.thumbnailFileUrl,
                smr.rating,
                smr.shared
            )
            from FurnitureModelResult smr
            join smr.user user
            where smr.deleted != true and smr.shared != false
            """
    )
    List<GalleryObjectListResponse> findGalleryObjectListResponseByUserId();

    @Query("select f from FurnitureModelResult f left join fetch f.user where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    Page<FurnitureModelResult> findByStatusPushed(@Param("statusPushed") Boolean statusPushed, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update FurnitureModelResult f set f.statusPushed = true where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    int updateStatusPushed(@Param("statusPushed") Boolean statusPushed);
}
