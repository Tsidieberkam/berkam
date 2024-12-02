package com.example.backend_ifc_foods.entite;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper =true )
public class Compte_Credit  extends Compte {
    @Transient
    private String errormessage;
    @Column(name= "credit")
    private double credit_amount;
    
}
