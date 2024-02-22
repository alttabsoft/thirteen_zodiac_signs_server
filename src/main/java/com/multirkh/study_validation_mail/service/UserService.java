package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.DTO.UserRegisterDTO;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void register(String siteURL, UserRegisterDTO userRegisterDTO){
        User user = new User(userRegisterDTO);
        userRepository.save(user);
    }
}
