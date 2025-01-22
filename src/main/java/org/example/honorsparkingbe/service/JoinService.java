package org.example.honorsparkingbe.service;


import org.example.honorsparkingbe.domain.entity.MemberEntity;
import org.example.honorsparkingbe.domain.enums.LoginPlatform;
import org.example.honorsparkingbe.domain.enums.MemberRole;
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
     */
    public void joinProcess(JoinDTO joinDTO) {

        // authId 중복 확인
        boolean isUser = userRepository.existsByAuthId(joinDTO.getAuthId());
        if (isUser) {
            throw new IllegalArgumentException("authId= "+ joinDTO.getAuthId() +" already exists.");
        }

        MemberEntity data = new MemberEntity();

        data.setAuthId(joinDTO.getAuthId());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setUserName(joinDTO.getUserName());
        data.setPhoneNumber(joinDTO.getPhoneNumber());
        data.setEmail(joinDTO.getEmail());
        data.setBirthdayYear(joinDTO.getBirthdayYear());
        data.setBirthday(joinDTO.getBirthday());
        data.setLoginPlatform(LoginPlatform.valueOf("NORMAL"));
        data.setRole(MemberRole.valueOf("ROLE_USER"));

        userRepository.save(data);
    }

}
