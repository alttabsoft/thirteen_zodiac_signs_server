package com.multirkh.study_validation_mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public Member(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Member() {

    }
}
