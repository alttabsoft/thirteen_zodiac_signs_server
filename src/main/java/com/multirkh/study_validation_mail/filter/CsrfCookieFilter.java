package com.multirkh.study_validation_mail.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) //요청당 단 한번의 로직만 실행된다.
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName()); //HttpServletRequest 인스턴스 내에 있는 CsrfToken 객체를 불러온다.(CsrfToken의 이름을 통해서 말이다.)
        if(null != csrfToken.getHeaderName()){  // 해당 토큰 인스턴스에 헤더 이름이 있는가? 이름이 있다는 것은 프레임워크가 CSRF 토큰을 생성했다는 뜻으로 해석할 수 있다.
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken()); // 그럼 그 토큰을 response에 싣는다. // 헤더를 작성했다면 쿠키는 스프링 프레임워크가 알아서 생성하고 응답 시 헤더로 보내는 것을 잊지 않고 알아서 해주게 된다.
        }
        filterChain.doFilter(request, response);
    }
}
