package com.example.backend_ifc_foods.dto;


import java.util.Date;


import com.example.backend_ifc_foods.entite.Compte;

import com.example.backend_ifc_foods.entite.Role;
import com.example.backend_ifc_foods.entite.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UtilisateurResponseDTO {
    private Long id_utilisateur;
    private String nom;
    private String telephone;
    private String quartier;
    private String ville;
    private String email;
    private String password;
    private Date date_inscription;
    private Role roles ;
    private Compte compte;
    private String errormessage;
    private Status status;

    
}
