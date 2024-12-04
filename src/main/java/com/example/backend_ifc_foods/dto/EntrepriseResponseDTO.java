package com.example.backend_ifc_foods.dto;


import java.util.ArrayList;
import java.util.List;

import com.example.backend_ifc_foods.entite.Assurance;
import com.example.backend_ifc_foods.entite.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntrepriseResponseDTO extends UtilisateurResponseDTO {
    private String domaine_activite; 
    private List<Employee> employes = new ArrayList<>();
    private List<Assurance> assurances= new ArrayList<>();
    
    
}
