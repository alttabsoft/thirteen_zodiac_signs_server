package com.alttabsof.thirteen_zodiac_signs_server.minio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
}
