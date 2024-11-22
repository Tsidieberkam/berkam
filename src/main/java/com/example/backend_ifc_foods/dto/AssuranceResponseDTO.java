package com.example.backend_ifc_foods.dto;


import java.util.ArrayList;

import java.util.List;

import com.example.backend_ifc_foods.entite.Entreprise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AssuranceResponseDTO extends UtilisateurResponseDTO {
    private String code_ifc;
    private List<Entreprise> entreprises = new ArrayList<>();
}
