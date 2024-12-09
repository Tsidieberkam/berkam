package com.example.backend_ifc_foods.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Status;


public interface EmployeRepository extends JpaRepository<Employee , Long> {
    public Employee findByEmail(String email);
    public List<Employee> findByStatus(Status status);

 
}
