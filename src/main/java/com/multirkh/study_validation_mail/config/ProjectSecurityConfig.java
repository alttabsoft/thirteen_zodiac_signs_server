package com.multirkh.study_validation_mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception { //Default 세팅이 있다. 그것은 모든 http url 에 대하여 전부 로그인 기능을 요구하는 것이다. 하지만 특정 Url 에서만 링크를 열어두는 방법이 존재한다.
        http.authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/register", "/").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/webjars/**").permitAll() // css 장식용
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
