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
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GCPFileUploader {

    private final Storage storage;

    private final String bucket;

    private static final int LIMIT_TIME = 400;
    private static final int CHUNK_SIZE = 1024 * 1024 * 200;
    private static final int START_IDX = 0;
    private static final int DEST_POS = 0;
    private static final int INITIAL_SIZE = 0;

    public GCPFileUploader(
            Storage storage,
            @Value("${spring.cloud.gcp.storage.bucket}") String bucket
    ) {
        this.storage = storage;
        this.bucket = bucket;
    }

    public String uploadVideo(final RawFileData fileData) {
        validateFileExists(fileData);
        validateFileDuration(fileData.getContent());
        return sendVideoToStorage(fileData);
    }

    private String sendVideoToStorage(final RawFileData fileData) {
        try (final InputStream inputStream = fileData.getContent()) {
            String fileName = fileData.getStoreFileName();
            byte[] fileBytes = inputStream.readAllBytes();
            int numChunks = (int) Math.ceil((double) fileBytes.length / CHUNK_SIZE);

            List<CompletableFuture<byte[]>> futures = new ArrayList<>();

            fileMemoryLoad(numChunks, fileBytes, futures);
            fileUpload(fileData, fileName, futures);

            return String.format("%s/%s/%s", "https://storage.googleapis.com", bucket, fileName);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }
    }

    private static void fileMemoryLoad(int numChunks, byte[] fileBytes, List<CompletableFuture<byte[]>> futures) {
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

    private void fileUpload(RawFileData fileData, String fileName, List<CompletableFuture<byte[]>> futures) throws IOException {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket, fileName)
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

            BlobInfo blobInfo = BlobInfo.newBuilder(bucket, fileName)
                    .setContentType(fileData.getContentType())
                    .build();

            return storage.create(blobInfo, inputStream)
                    .getMediaLink();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.");
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

            if (videoDuration < LIMIT_TIME) {
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

    private static double getVideoDuration(InputStream videoStream) throws ImageProcessingException, IOException, MetadataException {
        Metadata metadata = null;
        metadata = ImageMetadataReader.readMetadata(videoStream);
        Directory directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
        return Math.floor(directory.getDouble(Mp4Directory.TAG_DURATION_SECONDS));
    }
}
