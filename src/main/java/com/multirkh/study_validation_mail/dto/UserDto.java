package com.multirkh.study_validation_mail.dto;

import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private List<AuthorityDto> authorityDtoList = new ArrayList<AuthorityDto>();


    public UserDto(String email, String password, List<AuthorityDto> authorityDtoList) {
        this.email = email;
        this.password = password;
        this.authorityDtoList = authorityDtoList;
    }
}
