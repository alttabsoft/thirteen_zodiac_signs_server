package com.alttabsof.thirteen_zodiac_signs_server.repository;

import com.alttabsof.thirteen_zodiac_signs_server.RandomStringGenerator;
import com.alttabsof.thirteen_zodiac_signs_server.entity.Authority;
import com.alttabsof.thirteen_zodiac_signs_server.entity.User;
import com.alttabsof.thirteen_zodiac_signs_server.entity.UserAuthority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser() {
        User user = new User("ravikumar@example.com", passwordEncoder.encode("example"), RandomStringGenerator.generateRandomString(64));
        User savedMember = userRepository.save(user);
        User existUser = userRepository.findByEmail(user.getEmail()).get(0);
        assertThat(savedMember.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void testCreateUser2(){
        User user = new User("helloWorld@example.com", passwordEncoder.encode("helloWorld"), RandomStringGenerator.generateRandomString(64));
        Authority authority = new Authority("USER");
        Authority authority2 = new Authority("ADMIN");
        Authority savedAuthority = authorityRepository.save(authority);
        Authority savedAuthority2 = authorityRepository.save(authority2);
        User savedUser = userRepository.save(user);
        userAuthorityRepository.save(new UserAuthority(savedUser, savedAuthority));
        userAuthorityRepository.save(new UserAuthority(savedUser, savedAuthority2));
        System.out.println("");

    }
}