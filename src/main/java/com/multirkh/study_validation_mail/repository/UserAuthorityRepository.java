package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
