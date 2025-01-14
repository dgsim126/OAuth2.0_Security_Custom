package org.example.honorsparkingbe.dto;

import java.io.Serializable;
import java.util.Map;

public class KakaoResponse implements OAuth2Response, Serializable {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        // `kakao_account`에서 이메일을 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null && kakaoAccount.get("email") != null
                ? kakaoAccount.get("email").toString()
                : null; // 이메일 정보가 없을 경우 null 반환
    }

    @Override
    public String getName() {
        // `properties`에서 닉네임을 추출
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties != null && properties.get("nickname") != null
                ? properties.get("nickname").toString()
                : null; // 닉네임 정보가 없을 경우 null 반환
    }
}
