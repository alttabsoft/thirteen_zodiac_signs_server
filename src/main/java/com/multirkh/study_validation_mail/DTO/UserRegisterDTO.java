package com.multirkh.study_validation_mail.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class UserRegisterDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public String getEncryptPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
