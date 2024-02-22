package com.alttabsof.thirteen_zodiac_signs_server.config;

import com.alttabsof.thirteen_zodiac_signs_server.filter.CsrfCookieFilter;
import com.alttabsof.thirteen_zodiac_signs_server.filter.JWTTokenGeneratorFilter;
import com.alttabsof.thirteen_zodiac_signs_server.filter.JWTTokenValidatorFilter;
import com.alttabsof.thirteen_zodiac_signs_server.handler.SpaCsrfTokenRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityContext((context) -> context.requireExplicitSave(false))
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
                //.csrf(AbstractHttpConfigurer::disable)
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/register","/verify")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)         // 기본 인증 필터 다음, 해당 인증에 대한 결과물을 토큰 형태로 유지해야하기 때문에 이 메소드를 추가하는 것
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)        // 기본 인증 필터 이전에 실행되어야 함, 해당 JWTTokenValidatorFilter 가 정상적으로 동작했다면 Secur
                //.addFilterBefore(new RefreshTokenValidatorFilter(),JWTTokenValidatorFilter.class)     // jwt 토큰 만료시, refresh 토큰 수행
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user", "/account", "/upload").authenticated()
                        .requestMatchers("/register","/verify").permitAll()
                        .requestMatchers("/csrf").permitAll()
                )
                //.formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}