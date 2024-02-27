package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.furniture.FurnitureUploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FurnitureRepository extends JpaRepository<FurnitureUploadFile, Long> {
}
