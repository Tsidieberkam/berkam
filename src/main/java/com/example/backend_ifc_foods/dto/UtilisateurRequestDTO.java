package com.example.backend_ifc_foods.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UtilisateurRequestDTO {
    private String nom;
    private String telephone;
    private String quartier;
    private String ville;
    private String email;                                                                   
    private String password;
    private Date date_inscription;
    private long id_entreprise;
    
    
}
