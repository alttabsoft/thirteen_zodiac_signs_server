package com.multirkh.study_validation_mail.entity;

import com.multirkh.study_validation_mail.dto.UserInfoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(
                name = "EMAIL_UNIQUE",
                columnNames = {"email"}
        )
})
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
