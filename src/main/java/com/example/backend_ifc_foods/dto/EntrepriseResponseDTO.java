package com.example.backend_ifc_foods.dto;




import com.example.backend_ifc_foods.entite.Assurance;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntrepriseResponseDTO extends UtilisateurResponseDTO {
    private String domaine_activite; 
    private Assurance  assurance;
    
    
    
}
