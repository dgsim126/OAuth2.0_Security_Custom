package org.example.honorsparkingbe.service;


import org.example.honorsparkingbe.domain.UserEntity;
import org.example.honorsparkingbe.dto.JoinDTO;
import org.example.honorsparkingbe.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 회원가입 기본로직
     * @param joinDTO
     */
    public void joinProcess(JoinDTO joinDTO) {

        // username 중복 확인
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            throw new IllegalArgumentException("Username already exists.");
        }

        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setName(joinDTO.getName());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); // 비밀번호 암호화
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }

}
