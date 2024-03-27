package com.boostcamp.zzimkong.support;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SignedUrlGenerator {

    private static final String CLASS_PATH = "src/main/resources/";

    private final Storage storage;
    private final String bucket;
    private final String credentials;

    public SignedUrlGenerator(
            Storage storage,
            @Value("${spring.cloud.gcp.storage.bucket}") String bucket,
            @Value("${spring.cloud.gcp.storage.credentials.location}") String credentials
    ) {
        this.storage = storage;
        this.bucket = bucket;
        this.credentials = credentials;
    }

    public String generateSignedUrl(String objectPath) throws IOException {
        URL signedUrl = storage.signUrl(
                BlobInfo.newBuilder(BlobId.of(bucket, objectPath)).build(),
                5, TimeUnit.MINUTES, // URL의 유효 시간을 5분으로 설정
                Storage.SignUrlOption.signWith(ServiceAccountCredentials.fromStream(
                        new FileInputStream(CLASS_PATH + credentials)))
        );
        return signedUrl.toString();
    }
}