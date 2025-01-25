package org.example.honorsparkingbe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    @GetMapping("/info")
    public Map<String, Object> getSessionInfo() {
        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("userName", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());

        // JSON 응답 반환
        return response;
    }
}
