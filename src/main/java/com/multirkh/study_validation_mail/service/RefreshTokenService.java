package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.entity.RefreshToken;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.RefreshTokenRepository;
import com.multirkh.study_validation_mail.repository.UserRepository;
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
