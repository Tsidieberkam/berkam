package com.example.backend_ifc_foods.dto;

import com.example.backend_ifc_foods.entite.Entreprise;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeResponseDTO extends UtilisateurResponseDTO {
    private Entreprise entreprise;
    private long idcop;
    private String nom_entreprise;
}
