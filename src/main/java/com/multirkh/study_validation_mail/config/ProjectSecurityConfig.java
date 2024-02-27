package com.multirkh.study_validation_mail.config;

import com.multirkh.study_validation_mail.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler(); //스프링 프레임워크가 토큰값을 애플리케이션으로 전달할 때 필요한 핸들러
        requestHandler.setCsrfRequestAttributeName("_csrf"); // 해당 CsrfRequest속성의 이름을 만들기 // 사실 Default 이름이랑 동일한데 그냥 적어놓음

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); //소통하고 싶은 출처 기재
                    config.setAllowedMethods(Collections.singletonList("*")); //원하는 메소드(Post, Get, Push 등)를 특정
                    config.setAllowCredentials(true); // 인증 정보 주고 받는 것에 동의하는가?
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization")); // 브라우저에게 해당 헤더를 받아달라는 뜻이다. 이 헤더를 이용해 JWT 토큰을 보낼 수 있게 된다.
                    config.setMaxAge(3600L); // 이러한 설정은 한 시간동안 기억해두었다가 캐시로 전환 보통 프로덕션에서는 24, 7일, 30일로 설정함
                    return config;
                }))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/contact", "/register") //이 친구들은 CSRF에 대한 보호를 할 필요가 없으므로(민감한 정보를 수정하는 URL이 아니므로) 무시한다.
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) //CSRF 토큰 필터를 정의하고, 해당 필터가 BasicAuthenticationFilter(여기서 로그인 인증이 동작 후 , CSRF 토큰이 생성됨) 클래스 다음에 위치 하도록 설정

                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)         // 기본 인증 필터 다음, 해당 인증에 대한 결과물을 토큰 형태로 유지해야하기 때문에 이 메소드를 추가하는 것
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)        // 기본 인증 필터 이전에 실행되어야 함, 해당 JWTTokenValidatorFilter 가 정상적으로 동작했다면 Secur
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user", "/account").authenticated()
                        .requestMatchers("/register").permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
