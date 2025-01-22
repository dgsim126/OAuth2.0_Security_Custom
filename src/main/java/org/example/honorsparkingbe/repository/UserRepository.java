package org.example.honorsparkingbe.repository;

import org.example.honorsparkingbe.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MemberEntity, Integer> {
    boolean existsByAuthId(String authId);

    MemberEntity findByAuthId(String authId);
}