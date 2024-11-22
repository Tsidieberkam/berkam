package com.example.backend_ifc_foods.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntrepriseRequestDTO extends   UtilisateurRequestDTO {
    private String domaine_activite;   
  
}
