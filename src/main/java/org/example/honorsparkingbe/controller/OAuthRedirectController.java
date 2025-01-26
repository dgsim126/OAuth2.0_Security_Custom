package org.example.honorsparkingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OAuthRedirectController {

    /**
     * 소셜 로그인 요청 처리: 인증 URL로 리다이렉트
     */
    @GetMapping("/api/v1/auth/login/oauth/{provider}")
    public String redirectToProvider(@PathVariable String provider) {
        // Spring Security가 처리할 인증 경로로 리다이렉트
        return "redirect:/oauth2/authorization/" + provider;
    }
}
