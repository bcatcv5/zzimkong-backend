package com.boostcamp.zzimkong.support;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SignedUrlGenerator {
    private final Storage storage;;

    public SignedUrlGenerator(Storage storage) {
        this.storage = storage;
    }

    public String generateSignedUrl(String objectPath) throws IOException {
        URL signedUrl = storage.signUrl(
                BlobInfo.newBuilder(BlobId.of("버킷 이름", objectPath)).build(),
                5, TimeUnit.MINUTES, // URL의 유효 시간을 5분으로 설정
                Storage.SignUrlOption.signWith(ServiceAccountCredentials.fromStream(
                        new FileInputStream("key.json 경로")))
        );
        return signedUrl.toString();
    }
}