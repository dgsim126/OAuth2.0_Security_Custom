package org.example.honorsparkingbe.config;

/**
 * Spring Security 설정 클래스
 * - 접근 권한 설정, 로그인 로그아웃 및 세션 관리, CSRF 비활성화 등
 */

import org.example.honorsparkingbe.security.CustomAuthenticationSuccessHandler;
import org.example.honorsparkingbe.security.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {

        this.customOAuth2UserService = customOAuth2UserService;
    }

    /**
     * 비밀번호 암호화
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 관련 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // 접근 설정
        http
                .cors(Customizer.withDefaults()) // CORS 활성화 -- 250119 추가(이상 시 삭제)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/auth/login/**", "/login/**", "/oauth2/**", "/api/v1/auth/join", "/confirm", "/loginProc").permitAll()
                        .requestMatchers("/api/v1/admin").hasRole("ADMIN")                  // 해당 role만 접근 가능
                        .requestMatchers("/api/v1/my/**").hasAnyRole("ADMIN", "USER") // /api/v1/my/**만 허용
                        .anyRequest().authenticated());

        http
                .formLogin((formLogin) -> formLogin
                        .loginProcessingUrl("/loginProc") // 로그인 처리 경로 (HTML 폼의 action과 일치)
                        .successHandler(new CustomAuthenticationSuccessHandler()) // 커스텀 성공 핸들러 등록(json 반환)
                        .permitAll() // 로그인 페이지 접근 허용
                )

                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/api/v1/session/info", true) // 소셜 로그인 성공 후 이동 경로
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));


        // CSRF(Cross Site Request Forgery: 사이트 간 요청 위조)
        http
                .csrf((auth) -> auth.disable()); // 개발환경에서 csrf 비활성화

        http
                .httpBasic((basic) -> basic.disable());


        // CSRF 활성화 시 로그아웃을 GET 요청으로 하기 위해
//        http
//                .logout((auth) -> auth
//                        .logoutUrl("/api/v1/logout") // 로그아웃 경로
//                        .logoutSuccessUrl("/api/v1/") // 로그아웃 성공 시 리다이렉트 경로
//                );

        // 다중 로그인 설정
//        http
//                .sessionManagement((auth) -> auth
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true)
//                );

        // 세션 고정 공격 보호
//        http
//                .sessionManagement((auth) -> auth
//                        .sessionFixation().changeSessionId()
//                );


        return http.build();
    }

}
