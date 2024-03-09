package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.space.SpaceUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<SpaceUploadFile, Long> {
}
