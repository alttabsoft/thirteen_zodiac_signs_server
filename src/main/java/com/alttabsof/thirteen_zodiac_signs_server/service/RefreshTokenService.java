package com.alttabsof.thirteen_zodiac_signs_server.service;

import com.alttabsof.thirteen_zodiac_signs_server.entity.RefreshToken;
import com.alttabsof.thirteen_zodiac_signs_server.entity.User;
import com.alttabsof.thirteen_zodiac_signs_server.repository.RefreshTokenRepository;
import com.alttabsof.thirteen_zodiac_signs_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String userEmail){
        User foundUser = userRepository.findByEmail(userEmail).get(0);
        return refreshTokenRepository.save(new RefreshToken(
                UUID.randomUUID().toString(),
                new Date((new Date()).getTime() + 604800000), //1 week
                foundUser
        ));
    }
}
