package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

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
        Authority foundAuthority = authorityRepository.findByName(authority.getName());
        assertThat(foundAuthority.getName()).isEqualTo(authority.getName());
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