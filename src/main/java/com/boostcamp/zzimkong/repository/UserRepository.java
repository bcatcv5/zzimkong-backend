package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
