package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    //public User findByEmail(String email);

    Member findByEmail(String email);
}