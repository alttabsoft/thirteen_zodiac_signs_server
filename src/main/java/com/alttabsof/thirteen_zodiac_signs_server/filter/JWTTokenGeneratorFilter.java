package com.alttabsof.thirteen_zodiac_signs_server.filter;

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

import static com.alttabsof.thirteen_zodiac_signs_server.config.SecurityConstants.*;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     * @brief issuer :       해당 토큰을 발행하는 주체 <br/>
     * subject : 소제목이라 아무거나 넣어도 됨 <br/>
     * claim : 각 속성(attribute) 에 대한 값을 넣는 부분 <br/>
     * issuedAt : 토큰 발행일 <br/>
     * expiration : 토큰 만료일 //<br/>
     * signwith(key) :  서명값 (사전 설정한 JWT_KEY 값을 활용하여 저장한다. <br/>
     * &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;해당 키 값은 숨기는 것이 좋다.)
     * compact : 최종 구성하여 String 으로 return;
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(JWT_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer(COMPANY_NAME)
                    .subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 60000000))
                    .signWith(key)
                    .compact();
            response.setHeader(JWT_HEADER, jwt);
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