package com.multirkh.study_validation_mail.repository;

import com.multirkh.study_validation_mail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    //public User findByEmail(String email);

    User findByEmail(String email);
}