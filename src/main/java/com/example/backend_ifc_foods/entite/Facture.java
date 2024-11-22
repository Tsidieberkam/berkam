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
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_facture;
    @Column(name="nom_proprietaire")
    private String nom_proprietaire;
    @Column(name="date_facturation")
    private Date date_facturation;
    @Column(name="qrcode_fact")
    private String qrCodefact;
    @OneToOne
    @JoinColumn(name = "numero_transaction")
    private Transaction transaction;
}
