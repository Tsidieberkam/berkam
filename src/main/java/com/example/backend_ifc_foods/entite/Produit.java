package com.example.backend_ifc_foods.entite;

import java.util.ArrayList;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produit;
    @Column(name="nom_produit")
    private String nom;
    @Column(name="prix")
    private double prix;
    @Column(name="qrcode")
    private String qrcode;
    @ManyToOne
    @JoinColumn(name = "partenaire_shop_id") // Nom de la colonne de jointure
    private Partenaire_Shop partenaireShop;
    @ManyToMany(fetch = FetchType.LAZY  ,cascade = CascadeType.ALL)
    @JoinTable(name = "produit_document",joinColumns = @JoinColumn(name ="id_produit"),inverseJoinColumns = @JoinColumn(name = "id_document"))
    private List<Document> documents = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;
    @Transient
    private String errormessage;

    
    
    
}   
  
