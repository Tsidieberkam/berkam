package com.example.backend_ifc_foods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieResponseDTO {
    private Long id_categorie;
    private String nom_categorie;
    
}
