package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oauth);
}
