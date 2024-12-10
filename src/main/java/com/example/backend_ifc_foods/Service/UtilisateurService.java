package com.example.backend_ifc_foods.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.backend_ifc_foods.dto.AssuranceRequestDTO;
import com.example.backend_ifc_foods.dto.AssuranceResponseDTO;
import com.example.backend_ifc_foods.dto.EmployeRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeResponseDTO;
import com.example.backend_ifc_foods.dto.EntrepriseRequestDTO;
import com.example.backend_ifc_foods.dto.EntrepriseResponseDTO;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;



public interface UtilisateurService {
    public  ResponseEntity<?> inscrip(EmployeRequestDTO ures);
    public ResponseEntity<?> inscriptent(EntrepriseRequestDTO enrdto ,UtilisateurResponseDTO utilisateurConnecte);
    public List<EmployeResponseDTO> listemploye();
    public List<EntrepriseResponseDTO> listentreprise();
    public ResponseEntity<?> inscriptass(AssuranceRequestDTO assuranceRequestDTO );
    public List<AssuranceResponseDTO> listassurance();
    public UtilisateurResponseDTO connexBoolean(UtilisateurRequestDTO ur);
    public String generateOtp();
 
}
