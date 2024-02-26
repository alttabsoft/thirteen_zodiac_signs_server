package com.multirkh.study_validation_mail;

import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.entity.User;
import com.multirkh.study_validation_mail.entity.UserAuthority;
import com.multirkh.study_validation_mail.repository.AuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserAuthorityRepository;
import com.multirkh.study_validation_mail.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class StudyValidationMailApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StudyValidationMailApplication.class, args);
        AuthorityRepository authorityRepository = context.getBean(AuthorityRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        UserAuthorityRepository userAuthorityRepository = context.getBean(UserAuthorityRepository.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        // 저장할 Authority 엔티티 생성 후 저장
        Authority userAuthority = new Authority("USER");
        Authority adminAuthority = new Authority("ADMIN");
        Authority authorityUser = authorityRepository.save(userAuthority);
        Authority authorityAdmin = authorityRepository.save(adminAuthority);
        User user = new User("happy@example.com", passwordEncoder.encode("123456"),RandomStringGenerator.generateRandomString(64));
        user.setVerified();
        User savedUser = userRepository.save(user);
        userAuthorityRepository.save(new UserAuthority(savedUser, authorityUser));



    }
}
