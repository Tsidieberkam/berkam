package com.example.backend_ifc_foods.entite;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Assurance extends Utilisateur{
    @Column(name="code _ifc")
    private String code_ifc;
    @Transient
    private String errormesage;
   
    
}
