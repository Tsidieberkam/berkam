package com.example.backend_ifc_foods.entite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero_transaction;
    @Column(name= "montant")
    private double montant;
    @Column(name="date_transaction")
    private Date datecreation;
    @ManyToOne
    @JoinColumn(name = "numero_compte")
    private Compte compte;
    @OneToOne
    @JoinColumn(name = "id_facture")
    private Facture facture;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "transaction_produit",joinColumns = @JoinColumn(name ="numero_transaction"),inverseJoinColumns = @JoinColumn(name = "id_produit"))
    private List<Produit> produits = new ArrayList<>();
    
    

    
}
