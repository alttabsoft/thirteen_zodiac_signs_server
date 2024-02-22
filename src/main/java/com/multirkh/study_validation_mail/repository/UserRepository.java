package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    //public User findByEmail(String email);
    List<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.verificationcode = ?1")
    public User findByVerificationCode(String code);
}