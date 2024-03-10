package com.boostcamp.zzimkong.support;

import com.boostcamp.zzimkong.domain.message.Message;
import com.boostcamp.zzimkong.repository.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final MessageRepository messageRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public MessageConsumer(MessageRepository messageRepository, RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendSpaceMessage(String storeFileUrl, String type) {
        //TODO: 추론 테스트
        Message saveMessage = messageRepository.save(Message.of(type, "1709803553327.mp4"));
        messageRepository.save(saveMessage);

        String key = saveMessage.isSpace() ? "space" : "furniture";
        try {
            String value = objectMapper.writeValueAsString(saveMessage);
            redisTemplate.opsForList().rightPush(key, value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
