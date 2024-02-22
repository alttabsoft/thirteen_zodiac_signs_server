package com.multirkh.study_validation_mail.entity;

import com.multirkh.study_validation_mail.dto.UserInfoDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    //private String role;
    @OneToMany(mappedBy = "user")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserInfoDTO toUserInfoDTO() {
        return new UserInfoDTO(email, password, userAuthorityList);
    }
}
