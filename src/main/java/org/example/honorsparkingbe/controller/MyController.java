package org.example.honorsparkingbe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/api/v1/my")
    public String myPage(Model model) {
        // 현재 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            // 세션 전체 정보를 모델에 추가
            model.addAttribute("principal", principal);
            model.addAttribute("authentication", authentication);
        }

        return "my"; // Mustache 템플릿으로 반환
    }
}
