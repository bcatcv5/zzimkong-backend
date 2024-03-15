package com.boostcamp.zzimkong.repository.modelresult;

import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpaceResultRepository extends JpaRepository<SpaceModelResult, Long> {

    @Query("select f from SpaceModelResult f left join fetch f.user where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    Page<SpaceModelResult> findByStatusPushed(@Param("statusPushed") Boolean statusPushed, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update SpaceModelResult f set f.statusPushed = true where f.statusPushed = :statusPushed and f.statusCode != 'PROCESSING'")
    int updateStatusPushed(@Param("statusPushed") Boolean statusPushed);
}
