package com.multirkh.study_validation_mail.config;

import com.multirkh.study_validation_mail.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception { //Default 세팅이 있다. 그것은 모든 http url 에 대하여 전부 로그인 기능을 요구하는 것이다. 하지만 특정 Url 에서만 링크를 열어두는 방법이 존재한다.
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler(); //스프링 프레임워크가 토큰값을 애플리케이션으로 전달할 때 필요한 핸들러
        requestHandler.setCsrfRequestAttributeName("_csrf"); // 해당 CsrfRequest속성의 이름을 만들기 // 사실 csrfTokenRequestHandler에서 생성하는 Default 이름이랑 동일한데 그냥 적어놓음
        http
                //스프링 시큐리티가 제공하는 JSESSION ID가 아닌 내가 만든 SessionManageMent로 진행하고 싶을 때 써야하는 코드뭉치이다. 이게 없으면 매번 자격 증명(로그인)을 하게 된다.
                // .securityContext((context) -> context.requireExplicitSave(false)) //시큐리티 컨텍스트 홀더의 기능을 사용하지 않을 것이라는 뜻 시큐리티 컨텍스트안에 여러 정보가 들어가게 된다. //JWT 토큰을 사용하게 되면 더 이상 필요하지 않다. jsession은 지속적으로 유저의 상태를 확인하는 역할을 수행하게 되는데, 이를 jwt가 대체하기 때문이다
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // 첫 로그인이 완료되면 항상 JsessionID를 생성하라는 뜻이다.)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT는 무상태이기 때문에 Stateless로 놓는게 맞다
                //엔드 유저의 브라우저에서 UI 애플리케이션을 제공해주는 서버와 데이터를 제공해주는 백엔드 시스템의 URL이 서로 다르면 CORS 에러를 뿝는다. 이를 방지하기 위한 코드 설정이다.(백엔드에서 처리해야한다.)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); //소통하고 싶은 FRONTENT URL 기재
                    config.setAllowedMethods(Collections.singletonList("*")); //원하는 메소드(Post, Get, Push 등)를 특정
                    config.setAllowCredentials(true); // 인증 정보 주고 받는 것에 동의하는가?
                    config.setAllowedHeaders(Collections.singletonList("*")); // 원하는 헤더를 특정
                    config.setExposedHeaders(List.of("Authorization")); // 브라우저에게 해당 헤더를 받아달라는 뜻이다. 이 헤더를 이용해 JWT 토큰을 보낼 수 있게 된다.
                    config.setMaxAge(3600L); // 이러한 설정은 한 시간동안 기억해두었다가 캐시로 전환, 보통 프로덕션에서는 24, 7일, 30일로 설정함
                    return config;
                }))
                ///// CSRF 토큰 영역
                // 이 친구는 수상쩍은 링크로부터 해커가 보낸 요청을 원래 믿을만한 브라우저에서 온 요청인지를 구분하기 위해 사용하는 토큰이다.
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/register", "/", "/api/register", "/verify") //이 친구들은 CSRF에 대한 보호를 할 필요가 없으므로(민감한 정보를 수정하는 URL이 아니므로) 무시한다.
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                ///// 커스텀 필터 추가
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) //CSRF 토큰 필터를 정의하고, 해당 필터가 BasicAuthenticationFilter(여기서 로그인 인증이 동작 후 , CSRF 토큰이 생성됨) 클래스 다음에 위치 하도록 설정
                ///// 인증 후 권한 확인 영역
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/register", "/", "/api/register", "/verify").permitAll()
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
