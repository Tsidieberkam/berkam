package com.example.backend_ifc_foods.entite;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employes = new ArrayList<>();
    @Transient
    private String errormessage;
    @ManyToMany(mappedBy = "entreprises") // Indique que la relation est définie dans Assurance
    private List<Assurance> assurances = new ArrayList<>();
    
    
}
