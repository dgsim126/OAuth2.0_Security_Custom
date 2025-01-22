package org.example.honorsparkingbe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String authId;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;
    private int birthdayYear;
    private String birthday;
}
