package com.example.backend_ifc_foods.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ifc_foods.entite.Employee;

public interface EmployeRepository extends JpaRepository<Employee , Long> {
    
}
