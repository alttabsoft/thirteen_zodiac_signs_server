package com.alttabsof.thirteen_zodiac_signs_server.minio.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.alttabsof.thirteen_zodiac_signs_server.minio.config.MinioConstants.*;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(MINIO_URL)
                .credentials(MINIO_USERNAME, MINIO_PASSWORD)
                .build();
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(COMMON_BUCKET_NAME).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(COMMON_BUCKET_NAME)
                            .build()
            );
        }
        return client;
    }
}
