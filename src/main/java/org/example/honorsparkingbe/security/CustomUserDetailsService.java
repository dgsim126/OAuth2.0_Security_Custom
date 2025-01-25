package org.example.honorsparkingbe.security;

/**
 * Spring Security의 "인증" 과정을 담당
 * - 사용자 정보를 데이터베이스에서 조회하고, 조회된 사용자 정보를 Spring Security가 처리할 수 있는 형태로 반환
 */


import org.example.honorsparkingbe.domain.entity.MemberEntity;
import org.example.honorsparkingbe.security.CustomUserDetails;
import org.example.honorsparkingbe.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String authId) throws UsernameNotFoundException {
        System.out.println("로그인 시도: " + authId);
        MemberEntity userData = userRepository.findByAuthId(authId);
        if (userData == null) {
            System.out.println("유저 정보를 찾을 수 없음: " + authId);
            throw new UsernameNotFoundException("User not found with authId: " + authId);
        }
        System.out.println("유저 정보: " + userData);
        return new CustomUserDetails(userData);
    }
}
