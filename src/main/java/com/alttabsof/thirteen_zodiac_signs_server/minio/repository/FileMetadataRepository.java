package com.alttabsof.thirteen_zodiac_signs_server.minio.repository;

import com.alttabsof.thirteen_zodiac_signs_server.minio.entity.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, String> {
}
