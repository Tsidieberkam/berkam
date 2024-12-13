package com.example.backend_ifc_foods.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend_ifc_foods.Repository.UtilisateurRepository;
import com.example.backend_ifc_foods.entite.Status;
import com.example.backend_ifc_foods.entite.Utilisateur;

@Service
public class Comptesupprimesservice {

   
    @Autowired
    private UtilisateurRepository urespo;

    


    @Scheduled(cron = "0 0 0 * * ?") // Planifie l'exécution à minuit chaque jour
    public void deleteInactiveAccounts() {
        // Obtenir la date actuelle et soustraire 2 jours
        Date now = new Date();
        Date twoDaysAgo = new Date(now.getTime() - (2 * 24 * 60 * 60 * 1000)); // 2 jours en millisecondes

        // Récupérer tous les comptes inactifs créés avant cette date
        List<Utilisateur> inactiveAccounts = urespo.findByStatusAndDateinscriptionBefore(Status.INACTIF, twoDaysAgo);

        // Supprimer les comptes inactifs
        if (!inactiveAccounts.isEmpty()) {
            urespo.deleteAll(inactiveAccounts);
            System.out.println(inactiveAccounts.size() + " comptes inactifs supprimés.");
        } else {
            System.out.println("Aucun compte inactif à supprimer.");
        }
    }
}
