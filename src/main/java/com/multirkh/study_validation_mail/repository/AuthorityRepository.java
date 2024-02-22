package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
