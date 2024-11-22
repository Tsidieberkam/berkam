package com.example.backend_ifc_foods.entite;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "assurance_entreprise", joinColumns = @JoinColumn(name = "id_utilisateur"),inverseJoinColumns = @JoinColumn(name = "id_utilisateur_entreprise"))
    private List<Entreprise> entreprises = new ArrayList<>();
}
