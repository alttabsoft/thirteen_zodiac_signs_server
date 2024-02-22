package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testCreateUser() {
        Member member = new Member("ravikumar@gmail.com", "ravi2020", "Ravi", "Kumar");
        Member savedMember = memberRepository.save(member);
        Member existUser = memberRepository.findByEmail(member.getEmail());
        assertThat(member.getEmail()).isEqualTo(existUser.getEmail());
    }
}