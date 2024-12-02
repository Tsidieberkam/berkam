package com.example.backend_ifc_foods.Service;

import java.util.List;

import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Utilisateur;

public interface UtilisateurService {
    public List<UtilisateurResponseDTO> inscrip(List<UtilisateurRequestDTO> ures);
    public List<UtilisateurResponseDTO> listuser();
    public Utilisateur connexBoolean(UtilisateurRequestDTO ur);
}
