package com.example.backend_ifc_foods.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ifc_foods.entite.Assurance;
import com.example.backend_ifc_foods.entite.Status;


public interface AssuranceRepository extends JpaRepository<Assurance,Long> {
     public Assurance findByEmail(String email);
     public List<Assurance> findByStatus(Status status);
}
