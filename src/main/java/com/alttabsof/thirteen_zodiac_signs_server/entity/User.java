package com.alttabsof.thirteen_zodiac_signs_server.entity;

import com.alttabsof.thirteen_zodiac_signs_server.dto.AuthorityDto;
import com.alttabsof.thirteen_zodiac_signs_server.dto.UserDto;
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
        ),
        @UniqueConstraint(
                name = "VERIFICATION_UNIQUE",
                columnNames = {"verification_code"}
        )
})
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    private String email;
    private String password;
    @Column(name = "verification_code")
    private String verficationCode;
    @Getter
    private boolean verified = false;
    //private String role;
    @OneToMany(mappedBy = "user")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    public User(String email, String password, String verficationCode) {
        this.email = email;
        this.password = password;
        this.verficationCode = verficationCode;
    }

    public UserDto toUserDto() {
        List<AuthorityDto> authorityDtoList = this.userAuthorityList.stream().map(AuthorityDto::new).toList();
        return new UserDto(email, password, verficationCode, authorityDtoList);
    }

    public void setVerified(){
        this.verficationCode = null;
        this.verified = true;
    }

}
