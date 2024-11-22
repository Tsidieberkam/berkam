package com.example.backend_ifc_foods.entite;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id_stock;
    @Column(name = "quantite_stock")
    private double quantite_stock;
    @Column(name="date _mis_jour")
    private Date dateDernierAjust;
    @Column(name="emplacement")
    private String emplacement;
    @OneToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;
}
