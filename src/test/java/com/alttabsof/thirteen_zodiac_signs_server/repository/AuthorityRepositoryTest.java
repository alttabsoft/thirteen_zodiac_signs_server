package com.alttabsof.thirteen_zodiac_signs_server.repository;

import com.alttabsof.thirteen_zodiac_signs_server.entity.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Rollback(value = true)
class AuthorityRepositoryTest {
    @Autowired AuthorityRepository authorityRepository;

    @Test
    public void testCreateAuthority(){
        //given
        Authority authority = new Authority("USER");
        authorityRepository.save(authority);
        List<Authority> foundAuthority = authorityRepository.findByName(authority.getName());
        assertThat(foundAuthority.get(0).getName()).isEqualTo(authority.getName());
    }

    @Test
    public void testCreateAuthority2(){
        //given
        Authority authority = new Authority("USER");
        authorityRepository.save(authority);
        Authority authority2 = new Authority("USER");

        assertThrows(DataIntegrityViolationException.class, ()->authorityRepository.save(authority2));
    }
}