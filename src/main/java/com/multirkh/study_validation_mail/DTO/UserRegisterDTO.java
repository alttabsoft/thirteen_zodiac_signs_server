package com.multirkh.study_validation_mail.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
