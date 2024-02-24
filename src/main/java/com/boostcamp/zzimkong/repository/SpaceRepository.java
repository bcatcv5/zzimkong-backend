package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.space.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<UploadFile, Long> {
}
