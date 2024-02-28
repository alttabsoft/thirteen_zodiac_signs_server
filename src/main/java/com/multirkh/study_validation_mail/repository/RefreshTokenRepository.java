package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
