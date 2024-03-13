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

    public Long sendSpaceMessage(String storeFileUrl, String type) {
        Message saveMessage = messageRepository.save(Message.of(type, storeFileUrl));
        messageRepository.save(saveMessage);

        String key = saveMessage.isSpace() ? "space" : "furniture";
        try {
            String value = objectMapper.writeValueAsString(saveMessage);
            redisTemplate.opsForList().rightPush(key, value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return saveMessage.getId();
    }


}
