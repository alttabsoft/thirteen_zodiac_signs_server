package com.alttabsof.thirteen_zodiac_signs_server.minio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
public class FileMetaData {

    @Id
    private String id;

    private long size;

    private String httpContentType;

    public FileMetaData(String id, long size, String httpContentType){
        this.id = id;
        this.size = size;
        this.httpContentType = httpContentType;
    }
}
