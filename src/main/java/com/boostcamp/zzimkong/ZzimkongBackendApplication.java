package com.boostcamp.zzimkong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ZzimkongBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzimkongBackendApplication.class, args);
    }

}
