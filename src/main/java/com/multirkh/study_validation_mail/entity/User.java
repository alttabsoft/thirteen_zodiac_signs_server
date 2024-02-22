package com.multirkh.study_validation_mail.entity;

import com.multirkh.study_validation_mail.DTO.UserRegisterDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    public User(String email, String password, String firstName, String lastName, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User() {

    }
}
