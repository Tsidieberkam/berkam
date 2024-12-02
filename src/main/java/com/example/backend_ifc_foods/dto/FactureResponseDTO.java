package com.example.backend_ifc_foods.dto;

import com.example.backend_ifc_foods.entite.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureResponseDTO {
    private long id_facture;
    private Transaction transaction;
}
