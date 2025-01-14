package org.example.honorsparkingbe.controller;

/**
 * 회원가입 요청 처리 컨트롤러
 */

import org.example.honorsparkingbe.dto.JoinDTO;
import org.example.honorsparkingbe.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    /**
     * 회원가입
     * POST api/v1/join
     * @param joinDTO
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinPost(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.joinProcess(joinDTO);
            return ResponseEntity.status(202).body("Join process complete.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
