package com.example.backend_ifc_foods.Web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_ifc_foods.Service.UtilisateurService;
import com.example.backend_ifc_foods.dto.AssuranceRequestDTO;
import com.example.backend_ifc_foods.dto.AssuranceResponseDTO;
import com.example.backend_ifc_foods.dto.EmployeRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeResponseDTO;
import com.example.backend_ifc_foods.dto.EntrepriseRequestDTO;
import com.example.backend_ifc_foods.dto.EntrepriseResponseDTO;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;


import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class UtilisateurRestAPI {

    UtilisateurService us;

    public UtilisateurRestAPI(UtilisateurService us) {
        this.us = us;

    }
    

    @PostMapping(path = "/saveemploye")
    public List<EmployeResponseDTO> saveemplo(@RequestBody EmployeRequestDTO rst){
       return us.inscrip(rst);
    }

    @PostMapping(path ="/cunseru")
    public UtilisateurResponseDTO connexion(@RequestBody UtilisateurRequestDTO uri , HttpSession session){
        UtilisateurResponseDTO utilisateur = us.connexBoolean(uri);
        session.setAttribute("utilisateurConnecte", utilisateur);
        return utilisateur;
    }


    
    @PostMapping(path = "/saveentreprise")
    public List<EntrepriseResponseDTO> saveentrep(@RequestBody EntrepriseRequestDTO et , HttpSession session ){
       UtilisateurResponseDTO utilisateurConnecte = (UtilisateurResponseDTO) session.getAttribute("utilisateurConnecte");

       if (utilisateurConnecte == null){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Utilisateur non connecte");
       }


        return us.inscriptent(et, utilisateurConnecte);
    }

    @PostMapping(path = "/saveassurance")
    public List<AssuranceResponseDTO> saveassur(@RequestBody AssuranceRequestDTO ass  ){

        return us.inscriptass(ass);
    }

    @GetMapping(path= "/employee/liste")
    public List<EmployeResponseDTO> allemploye(){
        return us.listemploye();
    }

    @GetMapping(path= "/entreprise/liste")
    public List<EntrepriseResponseDTO> allentreprise(){
        return us.listentreprise();
    }


    @GetMapping(path= "/Assurance/liste")
    public List<AssuranceResponseDTO> allassurance(){
        return us.listassurance();
    }


}
