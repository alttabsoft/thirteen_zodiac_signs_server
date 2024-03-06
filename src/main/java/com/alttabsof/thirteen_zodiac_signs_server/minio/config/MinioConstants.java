package com.alttabsof.thirteen_zodiac_signs_server.minio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioConstants {
    public static String MINIO_URL;
    public static String MINIO_USERNAME;
    public static String MINIO_PASSWORD;
    public static String COMMON_BUCKET_NAME;
    public static Long OBJECT_PART_SIZE;
    public static Integer DEFAULT_CHUNK_SIZE;

    @Value("${minio.url}")
    private void setMinioUrl(String value){
        MINIO_URL = value;
    }

    @Value("${minio.username}")
    private void setUserName(String value){
        MINIO_USERNAME = value;
    }

    @Value("${minio.password}")
    private void setMinioPassword(String value){
        MINIO_PASSWORD = value;
    }

    @Value("${minio.common-bucket-name}")
    private void setCommonBucketName(String value){
        COMMON_BUCKET_NAME = value;
    }

    @Value("${minio.object-part-size}")
    private void setObjectPartSize(Long value){
        OBJECT_PART_SIZE = value;
    }

    @Value("${minio.default-chunk-size}")
    private void setDefaultChunkSize(Integer value){
        DEFAULT_CHUNK_SIZE = value;
    }
}
