package com.boostcamp.zzimkong.config;

import com.google.cloud.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class StorageConfig {

    @Bean
    public static Storage mockStorage() {
        return mock(Storage.class);
    }
}
