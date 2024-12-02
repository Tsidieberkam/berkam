package com.example.backend_ifc_foods.Web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_ifc_foods.Service.UtilisateurService;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Utilisateur;

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
    public List<UtilisateurResponseDTO> saveemplo(@RequestBody List<UtilisateurRequestDTO> rst){
       return us.inscrip(rst);
    }

    @PostMapping(path ="/cunseru")
    public Utilisateur connexion(@RequestBody UtilisateurRequestDTO uri , HttpSession session){
        Utilisateur utilisateur = us.connexBoolean(uri);
        session.setAttribute("utilisateur connecte", utilisateur);
        return utilisateur;
    }


}
