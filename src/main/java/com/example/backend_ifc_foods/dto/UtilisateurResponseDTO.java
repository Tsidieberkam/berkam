package com.example.backend_ifc_foods.dto;


import java.util.Date;
import java.util.HashSet;

import java.util.Set;

import com.example.backend_ifc_foods.entite.Compte;

import com.example.backend_ifc_foods.entite.Role;

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
    private Set<Role> roles = new HashSet<>();
    private Compte compte;
    private String errormessage;

    
}
