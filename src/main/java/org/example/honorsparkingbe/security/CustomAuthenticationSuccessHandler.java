package org.example.honorsparkingbe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.honorsparkingbe.dto.CustomOAuth2User;
import org.example.honorsparkingbe.dto.OAuth2Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // CustomOAuth2User로 캐스팅
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        OAuth2Response oAuth2Response = customOAuth2User.getOAuth2Response();

        // JSON 응답 데이터 구성
        Map<String, Object> responseData = new HashMap<>();

        String provider = oAuth2Response.getProvider();
        responseData.put("provider", provider);


        switch (provider) {
            case "google":
                // Google의 경우
                responseData.put("email", oAuth2Response.getEmail());
                responseData.put("displayName", oAuth2Response.getName());
                break;

            case "kakao":
                // Kakao의 경우
                responseData.put("nickname", oAuth2Response.getName());
                responseData.put("phone_number", oAuth2Response.getPhoneNumber());
                responseData.put("email", oAuth2Response.getEmail());
                responseData.put("birthyear", oAuth2Response.getBirthYear());
                responseData.put("birthday", oAuth2Response.getBirthday());
                break;

            case "naver":
                // Naver의 경우
                responseData.put("name", oAuth2Response.getName());
                responseData.put("mobile", oAuth2Response.getPhoneNumber());
                responseData.put("email", oAuth2Response.getEmail());
                responseData.put("birthyear", oAuth2Response.getBirthYear());
                responseData.put("birthday", oAuth2Response.getBirthday());
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported provider: " + provider);
                return;
        }

        // JSON 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }
}
