package com.boostcamp.zzimkong.support.file;

import com.boostcamp.zzimkong.domain.file.RawFileData;
import com.boostcamp.zzimkong.exception.InvalidDurationException;
import com.boostcamp.zzimkong.exception.UnExistFileException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.mp4.Mp4Directory;
import com.google.api.client.util.IOUtils;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.boostcamp.zzimkong.utils.ZzimkongConstant.START_IDX;
import static com.boostcamp.zzimkong.utils.ZzimkongConstant.UPLOAD_FAIL_MESSAGE;

@Slf4j
@Component
public class GCPFileUploader {

    private final Storage storage;

    private final String bucket;

    private static final int MAX = 600;
    private static final int MIN = 120;
    private static final int CHUNK_SIZE = 1024 * 1024 * 200;
    private static final int DEST_POS = 0;
    private static final int INITIAL_SIZE = 0;
    public static final String STORAGE_URL = "https://storage.googleapis.com";
    public static final String SPACE_URI = "space/video/";
    public static final String FURNITURE_URI = "furniture/img/";
    public static final String UPLOAD_PREFIX = "upload";
    public static final String UPLOAD_SUFFIX = "tmp";

    public GCPFileUploader(
            Storage storage,
            @Value("${spring.cloud.gcp.storage.bucket}") String bucket
    ) {
        this.storage = storage;
        this.bucket = bucket;
    }

    public String uploadVideo(final RawFileData fileData) {
        File tempFile = null;

        try (InputStream inputStream = fileData.getContent()) {
            tempFile = File.createTempFile(UPLOAD_PREFIX, UPLOAD_SUFFIX);

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(inputStream, out);
            }

            try (InputStream durationCheckStream = new FileInputStream(tempFile);
                 InputStream uploadStream = new FileInputStream(tempFile)) {

                validateFileExists(fileData);
                validateFileDuration(durationCheckStream);

                return sendVideoToStorage(fileData, uploadStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private String sendVideoToStorage(final RawFileData fileData, InputStream inputStream) {
        try {
            String fileName = fileData.getStoreFileName();
            byte[] fileBytes = inputStream.readAllBytes();
            int numChunks = (int) Math.ceil((double) fileBytes.length / CHUNK_SIZE);

            List<CompletableFuture<byte[]>> futures = new ArrayList<>();

            fileMemoryLoad(numChunks, fileBytes, futures);
            fileUpload(fileData, fileName, futures);

            return String.format("%s/%s/%s%s", STORAGE_URL, bucket, SPACE_URI, fileName);
        } catch (IOException e) {
            throw new RuntimeException(UPLOAD_FAIL_MESSAGE);
        }
    }

    private static void fileMemoryLoad(
            int numChunks,
            byte[] fileBytes,
            List<CompletableFuture<byte[]>> futures
    ) {
        for (int chunkIdx = START_IDX; chunkIdx < numChunks; chunkIdx++) {
            final int start = chunkIdx * CHUNK_SIZE;
            final int end = Math.min(start + CHUNK_SIZE, fileBytes.length);

            futures.add(CompletableFuture.supplyAsync(() -> {
                byte[] chunkBytes = new byte[end - start];
                System.arraycopy(fileBytes, start, chunkBytes, DEST_POS, end - start);
                return chunkBytes;
            }));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[INITIAL_SIZE]))
                .join();
    }

    private void fileUpload(
            RawFileData fileData,
            String fileName,
            List<CompletableFuture<byte[]>> futures
    ) throws IOException {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket, SPACE_URI + fileName)
                .setContentType(fileData.getContentType())
                .build();

        try (WriteChannel writer = storage.writer(blobInfo)) {
            for (CompletableFuture<byte[]> future : futures) {
                writer.write(ByteBuffer.wrap(future.join()));
            }
        }
    }

    public List<String> uploadImages(final List<RawFileData> rawFileDatas) {
        return rawFileDatas.stream()
                .map(this::uploadImage)
                .collect(Collectors.toList());
    }

    private String uploadImage(final RawFileData fileData) {
        validateFileExists(fileData);
        return sendImageToStorage(fileData);
    }

    private String sendImageToStorage(final RawFileData fileData) {
        try (final InputStream inputStream = fileData.getContent()) {
            String fileName = fileData.getStoreFileName();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucket, FURNITURE_URI + fileName)
                    .setContentType(fileData.getContentType())
                    .build();
            storage.create(blobInfo, inputStream);

            return String.format("%s/%s/%s%s", STORAGE_URL, bucket, FURNITURE_URI, fileName);
        } catch (IOException e) {
            throw new RuntimeException(UPLOAD_FAIL_MESSAGE);
        }
    }

    private void validateFileExists(final RawFileData file) {
        if (file == null) {
            throw new UnExistFileException();
        }
    }

    private void validateFileDuration(InputStream videoStream) {
        try {
            double videoDuration = getVideoDuration(videoStream);

            if (isNotInRange(videoDuration)) {
                throw new InvalidDurationException();
            }
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MetadataException e) {
            throw new RuntimeException(e);
        }
    }

    private static double getVideoDuration(InputStream videoStream)
            throws ImageProcessingException, IOException, MetadataException {
        Metadata metadata = null;
        metadata = ImageMetadataReader.readMetadata(videoStream);
        Directory directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
        return Math.floor(directory.getDouble(Mp4Directory.TAG_DURATION_SECONDS));
    }

    private static boolean isNotInRange(double videoDuration) {
        return videoDuration >= MAX || videoDuration <= MIN;
    }
}
