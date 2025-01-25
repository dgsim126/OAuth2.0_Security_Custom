package org.example.honorsparkingbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }
}
