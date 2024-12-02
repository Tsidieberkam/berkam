package com.example.backend_ifc_foods.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
public class StockRequestDTO {
    private double quantite_dispo;
    private Date dateDernierAjustement;
    private String emplacement;
}
