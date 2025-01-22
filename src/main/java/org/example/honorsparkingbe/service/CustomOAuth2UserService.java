package org.example.honorsparkingbe.service;

import org.example.honorsparkingbe.domain.entity.MemberEntity;
import org.example.honorsparkingbe.domain.enums.MemberRole;
import org.example.honorsparkingbe.dto.*;
import org.example.honorsparkingbe.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DefaultOAuth2UserService OAuth2UserService의 구현체

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        // OAuth provider
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // Provider마다 데이터를 주는 방식이 다르기에 다르게 처리(8강)
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        String authId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        MemberEntity existData = userRepository.findByAuthId(authId);
        String name= oAuth2Response.getName();

        String role = "ROLE_USER";
        if (existData == null) {

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setAuthId(authId);
            memberEntity.setEmail(oAuth2Response.getEmail());
            memberEntity.setRole(MemberRole.valueOf(role));
            memberEntity.setUserName(name);

            userRepository.save(memberEntity);
        }
        else {

            existData.setAuthId(authId);
            existData.setEmail(oAuth2Response.getEmail());

            role = String.valueOf(existData.getRole());

            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
