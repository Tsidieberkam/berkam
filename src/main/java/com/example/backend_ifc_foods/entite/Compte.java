package com.example.backend_ifc_foods.entite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero_compte;
    @Column(name ="solde")
    private double solde;
    @Column(name="date_creation")
    private Date date_creation;
    @OneToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "compte_transaction", joinColumns = @JoinColumn(name = "numero_compte"), inverseJoinColumns = @JoinColumn(name = "numero_transaction"))
    List<Transaction> transaction = new ArrayList<>();
    @Transient
    private String errormessage;
    

    
}
