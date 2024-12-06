package com.example.backend_ifc_foods.dto;

import java.util.Date;

import com.example.backend_ifc_foods.entite.Compte;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private Long numero_transaction;
    private Date date_transaction;
    private double montant;
    private String typetransaction;
    private Compte compte;
   
}
