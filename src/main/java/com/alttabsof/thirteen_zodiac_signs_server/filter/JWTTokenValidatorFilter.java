package com.alttabsof.thirteen_zodiac_signs_server.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.alttabsof.thirteen_zodiac_signs_server.config.SecurityConstants.JWT_HEADER;
import static com.alttabsof.thirteen_zodiac_signs_server.config.SecurityConstants.JWT_PRIVATE_KEY;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    /**
     *
     * @brief request.getHeader... : 헤더에 있는 "Authority"에 존재하는 데이터를 가져온다. <br/>
     * &emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp&emsp(json 문자열일 것으로 예상) <br/>
     * if (null != jwt) : 해당 문자열이 존재한다면 <br/>
     * Keys.hmacShaKeyFor... :  우선 서버내 비밀 키를 이용해 백엔드용 키를 생성한다.<br/>
     * Jwts.parser() : 클레임 객체를 생성한다. 일련의 과정을 거친다. parser을 호출한뒤 <br/>
     * verifyWith(key) : 해당 빌더가 시크릿 키를 이용해 빌드할 것을 알린다
     * build() : 그리고 빌드
     * parseSignedClaims(jwt) :  전송이 완료된 jwt 토큰을 읽을 거라고 말한다. 여기서 만료일을 지났는지 아닌지를 확인함
     * getPayload() : 페이로드에 들어있는 데이터를 읽고 이에 대한 변수를 claims 에 저장한다.
     * claims.get("username")) : 해당 claims 에서 username 파트를 저장
     * claims.get("authorities") :  해당 claims 에서 authorities 파트를 저장
     * UsernamePasswordAuthenticationToken : 인증 객체를 새로 생성하여 해당 데이터를 옮기는 과정
     * (credential: null) 패스워드는 저장하지 않는다. 이유는 인증과 관련한 정보 중 비밀번호는 그 정보의 특성상 Authentication 인스턴스에서 유지되고 있으면 안된다. 오로지 인증할 때만 확인하는 용도로 쓸 것
     * setAuthentication : 생성한 객체를 SecurityContextHolder에 저장해 놓는다. JWT 토큰을 인증했다는 것을 스프링 프레임워크가 확인할 수 있는 단계라고 할 수 있겠다.
     *
     * @throws Exception 이 모든 일련의 과정은 정상적인 토큰 키를 통해 데이터가 들어왔을 경우를 상정한다. 프로세스가 정상적으로 수행되지 않는다면 그것은 잘못된 토큰이다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(JWT_HEADER);
        if (null != jwt) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(JWT_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                String username = claims.get("username").toString();
                String authorities = claims.get("authorities").toString();
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null, //
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/user"); // 해당 링크에서 온 호출이다? 그럼 True 가 되면서 해당 Filter가 실행되어서는 안된다는 shouldNotFilter의 리턴값이 True가 된다. 따라서 해당 링크에서는 메소드의 이름에 알맞게 Validate 하지 않으며, 다른 모든 url api 호출에서는 Validate 과정을 거치게 되는 것이다.
    }
}
