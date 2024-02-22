package com.multirkh.study_validation_mail.dto;

import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import lombok.Getter;

@Getter
public class AuthorityDto {
    private String name;

    AuthorityDto(Authority authority){
        this.name = authority.getName();
    }
    public AuthorityDto(UserAuthority userAuthority){
        this.name = userAuthority.getAuthority().getName();
    }
}
