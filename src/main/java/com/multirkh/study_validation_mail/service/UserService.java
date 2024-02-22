package com.multirkh.study_validation_mail.service;

import com.multirkh.study_validation_mail.DTO.UserRegisterDTO;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String siteURL, UserRegisterDTO userRegisterDTO){
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        String email = userRegisterDTO.getEmail();
        String firstName = userRegisterDTO.getFirstName();
        String lastName = userRegisterDTO.getLastName();
        User user = new User(email,encodedPassword, firstName, lastName, "USER");
        userRepository.save(user);
    }
}
