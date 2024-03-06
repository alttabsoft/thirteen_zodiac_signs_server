package com.alttabsof.thirteen_zodiac_signs_server.minio.data;

import com.alttabsof.thirteen_zodiac_signs_server.minio.entity.FileMetaData;


public record ChunkWithMetadata(
        FileMetaData metadata,
        byte[] binaryData
) {}
