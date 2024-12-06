package com.example.backend_ifc_foods.dto;

import java.util.Date;



import com.example.backend_ifc_foods.entite.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteResponseDTO {
    private Long numero_compte;
    private Date date_creation;
    private double solde;
    private Utilisateur utilisateur;
    private String typeCompte;

    
}
