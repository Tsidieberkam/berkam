package com.example.backend_ifc_foods.Repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.backend_ifc_foods.entite.Status;
import com.example.backend_ifc_foods.entite.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    public Utilisateur findByEmail(String email);
    public Utilisateur findByEmailAndPassword(String email , String password);
    public Utilisateur findByNomAndEmail(String ko , String kl);
    //public Utilisateur findByNomAndPrenom(String nom , String prenom);
    List<Utilisateur> findByStatusAndDateinscriptionBefore(Status status, Date dateinscription);
}
