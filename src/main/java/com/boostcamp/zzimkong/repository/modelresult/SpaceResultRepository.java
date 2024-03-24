package com.boostcamp.zzimkong.repository.modelresult;

import com.boostcamp.zzimkong.controller.dto.interior.GalleryObjectListResponse;
import com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse;
import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpaceResultRepository extends JpaRepository<SpaceModelResult, Long> {
//    List<ObjectListResponse> findSpaceModelResultByUserId(Long userId);

    Optional<SpaceModelResult> findSpaceModelResultByUserIdAndId(Long userId, Long id);

    Optional<SpaceModelResult> findByIdAndSharedIsAndDeletedIs(Long id, boolean shared, boolean deleted);

    Optional<SpaceModelResult> findSpaceModelResultByUserIdAndIdAndDeletedIs(Long user_id, Long id, boolean deleted);

    @Query("""
            select
            new com.boostcamp.zzimkong.controller.dto.interior.ObjectListResponse(
                smr.id,
                smr.uploadFileName,
                smr.statusCode,
                smr.statusMessage,
                smr.storeFileUrl,
                smr.thumbnailFileUrl,
                smr.rating,
                smr.shared
            )
            from SpaceModelResult smr
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
                smr.uploadFileName,
                smr.statusCode,
                smr.statusMessage,
                smr.storeFileUrl,
                smr.thumbnailFileUrl,
                smr.rating,
                smr.shared
            )
            from SpaceModelResult smr
            join smr.user user
            where smr.deleted != true and smr.shared != false
            """
    )
    List<GalleryObjectListResponse> findGalleryObjectListResponseByUserId();

    @Query("select f from SpaceModelResult f left join fetch f.user where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    Page<SpaceModelResult> findByStatusPushed(@Param("statusPushed") Boolean statusPushed, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update SpaceModelResult f set f.statusPushed = true where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    int updateStatusPushed(@Param("statusPushed") Boolean statusPushed);
}
