package com.alttabsof.thirteen_zodiac_signs_server.repository;

import com.alttabsof.thirteen_zodiac_signs_server.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByName(String name);
}
