package com.alttabsof.thirteen_zodiac_signs_server.repository;

import com.alttabsof.thirteen_zodiac_signs_server.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
