package com.alttabsof.thirteen_zodiac_signs_server.minio;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VideoService {

    UUID save(MultipartFile video);

    DefaultVideoService.ChunkWithMetadata fetchChunk(UUID uuid, Range range);
}
