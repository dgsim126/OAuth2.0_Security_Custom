package org.example.honorsparkingbe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/session")
public class SessionController {

    @GetMapping("/info")
    public String getSessionInfo(Model model) {
        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 응답 데이터 생성
        model.addAttribute("username", authentication.getName()); // 사용자 이름
        System.out.println("NAME: "+ authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities()); // 권한
        model.addAttribute("authenticated", authentication.isAuthenticated()); // 인증 여부

        // Principal 객체에 추가 정보가 있는 경우 처리
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) principal;
            model.addAttribute("additionalInfo", user.getUsername()); // 추가 정보 예시
        } else {
            model.addAttribute("principal", principal.toString());
        }

        // Mustache 템플릿 파일 이름 반환
        return "session-info";
    }
}
