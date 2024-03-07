package com.alttabsof.thirteen_zodiac_signs_server.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public final class CustomLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 요청 헤더 출력
        System.out.println("Request Headers:");
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));

        // 요청 페이로드 출력 (POST 요청의 경우)
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Request Payload:");
            // 여기에서 request.getInputStream() 또는 request.getReader()를 사용하여 페이로드를 읽고 출력할 수 있습니다.
        }

        // 다음 필터로 체인 전달
        filterChain.doFilter(request, response);
    }
}