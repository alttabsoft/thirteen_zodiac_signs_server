package com.alttabsof.thirteen_zodiac_signs_server.minio;

import com.alttabsof.thirteen_zodiac_signs_server.minio.config.MinioConfig;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static com.alttabsof.thirteen_zodiac_signs_server.minio.config.MinioConstants.COMMON_BUCKET_NAME;
import static com.alttabsof.thirteen_zodiac_signs_server.minio.config.MinioConstants.OBJECT_PART_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService {

    private final MinioClient minioClient;

    // 저장
    public void save(MultipartFile file, UUID uuid) throws Exception {
        minioClient.putObject(
                PutObjectArgs
                .builder()
                .bucket(COMMON_BUCKET_NAME)
                .object(uuid.toString())
                .stream(file.getInputStream(), file.getSize(), OBJECT_PART_SIZE)
                .build()
        );
    }

    /**
     * @param uuid : 파일의 UUID
     * @param offset : 전체 파일 중 중간이든 어디든 시작 위치
     * @param length : 전송하는 파일 크기
     * @return :
     * @throws Exception
     */
    public InputStream getInputStream(UUID uuid, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(COMMON_BUCKET_NAME)
                        .offset(offset)
                        .length(length)
                        .object(uuid.toString())
                        .build());
    }
}
