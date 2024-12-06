package com.example.backend_ifc_foods.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.backend_ifc_foods.entite.Categorie;
import com.example.backend_ifc_foods.entite.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor @NoArgsConstructor
public class ProduitResponseDTO {
    private Long id_produit;
    private String nom;
    private double prix;
    private String qrcode;
    private List<Document> documents = new ArrayList<>();
    private Categorie categorie;
   
}
