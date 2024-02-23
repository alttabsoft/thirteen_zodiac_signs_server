package com.multirkh.study_validation_mail.filter;

import com.multirkh.study_validation_mail.config.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.multirkh.study_validation_mail.config.SecurityConstants.COMPANY_NAME;
import static com.multirkh.study_validation_mail.config.SecurityConstants.JWT_KEY;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            log.info("/////////  JwtKey = {} ////////////", JWT_KEY);
            SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8)); //키 발행
            String jwt = Jwts.builder()
                    .issuer(COMPANY_NAME) // 해당 토큰을 발행하는 주체
                    .subject("JWT Token") // 소제목이라 아무거나 넣어도 됨
                    .claim("username", authentication.getName()) //인증과정에서 넘어온 유저이름, 비밀번호는 있으면 안됨, jwt 토큰을 쉽게 디코딩할 수 있음(이게 비밀번호가 존재하면 JWT 디코딩 난이도가 쉬워진다는 것인지, 아니면, JWT 디코딩 난이도가 쉬워서 비밀번호가 노출되기 쉽다는 의미인지는 잘 모르겠음), 그리고 애플리케이션은 이메일, 유저 이름, 권한등은 요구할 수 있으나, 비밀번호는 요구해서는 안됨.
                    .claim("authorities", populateAuthorities(authentication.getAuthorities())) // 인증과정에서 넘어온 권한 모음
                    .issuedAt(new Date()) // 토큰 발행일
                    .expiration(new Date((new Date()).getTime() + 30000000)) // 토큰 만료일
                    .signWith(key).compact(); // 서명값 (사전 설정한 JWT_KEY 값을 활용하여 저장한다. 해당 키 값은 숨기는것이 좋다.)
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/user"); //해당 링크에서 온 요청이 아닌이상, 해당 필터가 실행되어 JWT 토큰을 재발행할 리소스를 추가 생성하지 않도록 방지. 다른 URL 에서는 해당 값이 True 이며, /user에서만 false 값을 가진다. 즉 /user 링크에서만 토큰이 재발행된다는 뜻이다.
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) { // 모든 권한을 읽어오는 메소드
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
