package com.alttabsof.thirteen_zodiac_signs_server.dto;

import com.alttabsof.thirteen_zodiac_signs_server.entity.Authority;
import com.alttabsof.thirteen_zodiac_signs_server.entity.UserAuthority;
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
