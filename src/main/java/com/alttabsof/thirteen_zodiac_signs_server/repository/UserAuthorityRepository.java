package com.alttabsof.thirteen_zodiac_signs_server.repository;

import com.alttabsof.thirteen_zodiac_signs_server.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
