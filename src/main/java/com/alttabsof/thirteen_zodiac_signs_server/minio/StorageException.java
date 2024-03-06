package com.alttabsof.thirteen_zodiac_signs_server.minio;

public class StorageException extends RuntimeException {

    public StorageException(Exception ex) {
        super(ex);
    }
}
