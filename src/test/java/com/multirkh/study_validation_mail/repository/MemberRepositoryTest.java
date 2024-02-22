package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.User;
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
    private UserRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUser() {
        User member = new User("ravikumar@gmail.com", passwordEncoder.encode("ravi2020"), "USER");
        User savedMember = memberRepository.save(member);
        User existUser = memberRepository.findByEmail(member.getEmail()).get(0);
        assertThat(savedMember.getEmail()).isEqualTo(existUser.getEmail());
    }
}