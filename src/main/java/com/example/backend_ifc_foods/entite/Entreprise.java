package com.example.backend_ifc_foods.entite;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= true)
public class Entreprise extends Utilisateur{
    @Column(name = "secteur_activite")
    private String domaine_activite;
    @Transient
    private String errormessage;
    @ManyToOne
    @JoinColumn(name = "id_assurance", nullable = true)
    private Assurance  assurance;
    
    
    
}
