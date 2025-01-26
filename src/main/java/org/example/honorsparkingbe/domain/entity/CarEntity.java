package org.example.honorsparkingbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.honorsparkingbe.domain.enums.CarType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "car")


public class CarEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 입차 시간
    private LocalDateTime entranceTime;

    // 차량 번호
    @Column(nullable = false)
    private String carNumber;

    // 차종 (Enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    // 전기차 여부
    private boolean isElectric;
}
