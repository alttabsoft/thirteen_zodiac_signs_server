package com.alttabsof.thirteen_zodiac_signs_server.minio.service;

import com.alttabsof.thirteen_zodiac_signs_server.minio.Range;
import com.alttabsof.thirteen_zodiac_signs_server.minio.data.ChunkWithMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    UUID save(MultipartFile video);

    ChunkWithMetadata fetchChunk(UUID uuid, Range range);
}
