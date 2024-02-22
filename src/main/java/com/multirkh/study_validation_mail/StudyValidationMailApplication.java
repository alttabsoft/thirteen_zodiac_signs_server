package com.multirkh.study_validation_mail;

import com.multirkh.study_validation_mail.entity.Authority;
import com.multirkh.study_validation_mail.repository.AuthorityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudyValidationMailApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StudyValidationMailApplication.class, args);
        AuthorityRepository authorityRepository = context.getBean(AuthorityRepository.class);

        // 저장할 Authority 엔티티 생성 후 저장
        Authority userAuthority = new Authority("USER");
        Authority adminAuthority = new Authority("ADMIN");
        authorityRepository.save(userAuthority);
        authorityRepository.save(adminAuthority);
    }
}
