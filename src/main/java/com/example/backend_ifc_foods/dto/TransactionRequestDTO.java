package com.example.backend_ifc_foods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private double montant;
    private long mumero_compte_destination;
    private String nom_acheteur;
    
}
