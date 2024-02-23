package com.multirkh.study_validation_mail.dto;

import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String verificationCode;
    private List<AuthorityDto> authorityDtoList = new ArrayList<AuthorityDto>();


    public UserDto(String email, String password, String verificationCode, List<AuthorityDto> authorityDtoList) {
        this.email = email;
        this.password = password;
        this.authorityDtoList = authorityDtoList;
        this.verificationCode = verificationCode;
    }

    public String getFullName(){
        return "We don't use full name not yet ~.,~";
    }
}
