package com.example.backend_ifc_foods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Partenaire_ShopRequestDTO extends UtilisateurRequestDTO {
    private String domaine;
    
}
