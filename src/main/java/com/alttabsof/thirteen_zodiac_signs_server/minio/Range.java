package com.alttabsof.thirteen_zodiac_signs_server.minio;

import static com.alttabsof.thirteen_zodiac_signs_server.minio.config.MinioConstants.DEFAULT_CHUNK_SIZE;

public class Range {

    private final long start;

    private final long end;

    public long getRangeStart() {
        return start;
    }

    public long getRangeEnd(long fileSize) {
        return Math.min(end, fileSize - 1);
    }

    public Range(long start, long end){
        this.start = start;
        this.end = end;
    }

    public static Range parseHttpRangeString(String httpRangeString) {
        // null 이면
        if (httpRangeString == null) {
            // 0 부터 기본 청크 바이트 길이까지
            return new Range(0, DEFAULT_CHUNK_SIZE);
        } else {
            // 아니면
            int dashIndex = httpRangeString.indexOf("-");
            long startRange = Long.parseLong(httpRangeString.substring(6, dashIndex));
            String endRangeString = httpRangeString.substring(dashIndex + 1);
            if (endRangeString.isEmpty()) {
                return new Range(startRange, startRange + DEFAULT_CHUNK_SIZE);
            }
            long endRange = Long.parseLong(endRangeString);
            return new Range(startRange, endRange);
        }
    }
}
