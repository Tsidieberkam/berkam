package com.example.backend_ifc_foods.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ifc_foods.entite.Compte;

public interface CompteRepository extends JpaRepository<Compte , Long> {
     
}
