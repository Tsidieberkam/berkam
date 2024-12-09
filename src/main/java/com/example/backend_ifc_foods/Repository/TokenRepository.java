package com.example.backend_ifc_foods.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ifc_foods.entite.ConfirmationToken;

public interface TokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String token);
    void deleteByToken(String token);
}