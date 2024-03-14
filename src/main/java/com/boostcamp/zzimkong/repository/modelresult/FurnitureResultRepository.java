package com.boostcamp.zzimkong.repository.modelresult;

import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FurnitureResultRepository extends JpaRepository<FurnitureModelResult, Long> {

    @Query("select f from FurnitureModelResult f left join fetch f.user where f.statusPushed = :statusPushed")
    Page<FurnitureModelResult> findByStatusPushed(@Param("statusPushed") Boolean statusPushed, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update FurnitureModelResult f set f.statusPushed = true where f.statusPushed = :statusPushed")
    int updateStatusPushed(@Param("statusPushed") Boolean statusPushed);
}
