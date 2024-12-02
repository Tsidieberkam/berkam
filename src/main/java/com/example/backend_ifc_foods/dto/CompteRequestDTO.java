package com.example.backend_ifc_foods.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteRequestDTO {
    private double solde;
    private String typeCompte;
    
}
