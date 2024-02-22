package com.multirkh.study_validation_mail.entity;

import com.multirkh.study_validation_mail.dto.UserInfoDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @Column(name = "user_id")
    private int id;
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public UserInfoDTO toUserInfoDTO() {
        return new UserInfoDTO(email, password, role);
    }
}
