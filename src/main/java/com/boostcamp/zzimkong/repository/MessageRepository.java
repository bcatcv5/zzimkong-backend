package com.boostcamp.zzimkong.repository;

import com.boostcamp.zzimkong.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
