package com.alttabsof.thirteen_zodiac_signs_server.minio.service;

import com.alttabsof.thirteen_zodiac_signs_server.minio.MinioStorageService;
import com.alttabsof.thirteen_zodiac_signs_server.minio.Range;
import com.alttabsof.thirteen_zodiac_signs_server.minio.StorageException;
import com.alttabsof.thirteen_zodiac_signs_server.minio.data.ChunkWithMetadata;
import com.alttabsof.thirteen_zodiac_signs_server.minio.entity.FileMetaData;
import com.alttabsof.thirteen_zodiac_signs_server.minio.repository.FileMetadataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultFileService implements FileService {

    private final MinioStorageService storageService;
    private final FileMetadataRepository fileMetadataRepository;

    @Override
    @Transactional
    public UUID save(MultipartFile video) {
        try {
            UUID fileUuid = UUID.randomUUID();
            FileMetaData fileMetaData = new FileMetaData(fileUuid.toString(), video.getSize(), video.getContentType());
            fileMetadataRepository.save(fileMetaData);
            storageService.save(video, fileUuid);
            return fileUuid;
        } catch (Exception ex) {
            log.error("Exception occurred when trying to save the file", ex);
            throw new StorageException(ex);
        }
    }

    /**
     * @param uuid : 해당 파일의 UUID
     * @param range : 해당 파일 청크의 길이
     * @param fileSize : 파일 크기
     * @return storageService 를 이용해 값을 해당 UUID의 일부분을 불러오고, 해당 파일을 Bytes로 읽어 return 합니다.
     */
    private byte[] readChunk(UUID uuid, Range range, long fileSize) {
        // 파일 사이즈, range를 토대로
        long startPosition = range.getRangeStart();
        long endPosition = range.getRangeEnd(fileSize);
        int chunkSize = (int) (endPosition - startPosition + 1);
        try(InputStream inputStream = storageService.getInputStream(uuid, startPosition, chunkSize)) {
            return inputStream.readAllBytes();
        } catch (Exception exception) {
            log.error("Exception occurred when trying to read file with ID = {}", uuid);
            throw new StorageException(exception);
        }
    }

    @Override
    public ChunkWithMetadata fetchChunk(UUID uuid, Range range) {
        FileMetaData fileMetadata = fileMetadataRepository.findById(uuid.toString()).orElseThrow(); // 메타 데이터 탐색
        return new ChunkWithMetadata(fileMetadata, readChunk(uuid, range, fileMetadata.getSize()));
    }
}
