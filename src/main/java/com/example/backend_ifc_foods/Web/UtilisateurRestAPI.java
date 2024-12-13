package com.example.backend_ifc_foods.Web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_ifc_foods.Repository.AssuranceRepository;
import com.example.backend_ifc_foods.Repository.EntrepriseRepository;
import com.example.backend_ifc_foods.Repository.Partenaire_ShopRepository;
import com.example.backend_ifc_foods.Service.OtpService;
import com.example.backend_ifc_foods.Service.TokenService;
import com.example.backend_ifc_foods.Service.UtilisateurService;
import com.example.backend_ifc_foods.dto.AssuranceRequestDTO;
import com.example.backend_ifc_foods.dto.AssuranceResponseDTO;
import com.example.backend_ifc_foods.dto.EmployeRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeResponseDTO;
import com.example.backend_ifc_foods.dto.EntrepriseRequestDTO;
import com.example.backend_ifc_foods.dto.EntrepriseResponseDTO;
import com.example.backend_ifc_foods.dto.OtpDataRequestDTO;
import com.example.backend_ifc_foods.dto.Partenaire_ShopRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Assurance;
import com.example.backend_ifc_foods.entite.ConfirmationToken;
import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Entreprise;
import com.example.backend_ifc_foods.entite.Partenaire_Shop;
import com.example.backend_ifc_foods.entite.Status;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class UtilisateurRestAPI {

    UtilisateurService us;
    OtpService ot;
    AssuranceRepository assrr;
    TokenService ts;
    EntrepriseRepository ets;
    Partenaire_ShopRepository psr;

    public UtilisateurRestAPI(UtilisateurService us, OtpService ot, AssuranceRepository assrr, TokenService ts,
            EntrepriseRepository ets ,Partenaire_ShopRepository psr) {
        this.us = us;
        this.ot = ot;
        this.assrr = assrr;
        this.ts = ts;
        this.ets = ets;
        this.psr=psr;
    }

    @PostMapping(path = "/saveemploye")
    public ResponseEntity<?> saveemplo(@RequestBody EmployeRequestDTO rst) {
        return us.inscrip(rst);
    }

    @PostMapping(path = "/verifyotp")
    public ResponseEntity<?> saveemplo(@RequestBody OtpDataRequestDTO opytrd) {
        return ot.verifyOtp(opytrd);
    }

    @PostMapping(path = "/cunseru")
    public UtilisateurResponseDTO connexion(@RequestBody UtilisateurRequestDTO uri, HttpSession session) {
        UtilisateurResponseDTO utilisateur = us.connexBoolean(uri);
        session.setAttribute("utilisateurConnecte", utilisateur);
        return utilisateur;
    }

    @PostMapping(path = "/saveentreprise")
    public ResponseEntity<?> saveentrep(@RequestBody EntrepriseRequestDTO et, HttpSession session) {
        UtilisateurResponseDTO utilisateurConnecte = (UtilisateurResponseDTO) session
                .getAttribute("utilisateurConnecte");

        if (utilisateurConnecte == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non connecte");
        }

        return us.inscriptent(et, utilisateurConnecte);
    }

    @PostMapping(path = "/saveassurance")
    public ResponseEntity<?> saveassur(@RequestBody AssuranceRequestDTO ass) {
        return us.inscriptass(ass);
    }

    @PostMapping(path = "/savepartenaireshop")
    public ResponseEntity<?> savepart(@RequestBody Partenaire_ShopRequestDTO prs) {
        return us.insparr(prs);
    }

    @GetMapping(path = "/employee/liste")
    public List<EmployeResponseDTO> allemploye() {
        return us.listemploye();
    }

    @GetMapping(path = "/entreprise/liste")
    public List<EntrepriseResponseDTO> allentreprise() {
        return us.listentreprise();
    }

    @GetMapping(path = "/Assurance/liste")
    public List<AssuranceResponseDTO> allassurance() {
        return us.listassurance();
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        // Valider le token
        ConfirmationToken tokenData = ts.getTokenData(token);
        if (tokenData == null) {

            List<Assurance> inactiveAssurances = assrr.findByStatus(Status.INACTIF);

            if (inactiveAssurances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun employé inactif trouvé.");
            }

            assrr.deleteAll(inactiveAssurances);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token invalide ou expiré.");
        }

        // Récupérer l'assurance via l'email
        Assurance assurance = assrr.findByEmail(tokenData.getEmail());
        if (assurance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assurance non trouvée.");
        }

        // Activer l'assurance
        assurance.setStatus(Status.ACTIF);
        assrr.save(assurance);

        // Supprimer le token
        ts.deleteToken(token);

        return ResponseEntity.status(HttpStatus.OK).body("Compte confirmé avec succès.");
    }

    @GetMapping("/confirm/entreprise")
    public ResponseEntity<?> confirmAccountE(@RequestParam("token") String token) {
        // Valider le token
        ConfirmationToken tokenData = ts.getTokenData(token);
        if (tokenData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token invalide ou expiré.");
        }

        // Récupérer l'assurance via l'email
        Entreprise entreprise = ets.findByEmail(tokenData.getEmail());
        if (entreprise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entreprise non trouvée.");
        }

        // Activer l'assurance
        entreprise.setStatus(Status.ACTIF);
        ets.save(entreprise);

        // Supprimer le token
        ts.deleteToken(token);

        return ResponseEntity.status(HttpStatus.OK).body("Compte confirmé avec succès.");
    }


    @GetMapping("/confirm/partenaire_shop")
    public ResponseEntity<?> confirmAccountP(@RequestParam("token") String token) {
        // Valider le token
        ConfirmationToken tokenData = ts.getTokenData(token);
        if (tokenData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token invalide ou expiré.");
        }

        // Récupérer l'assurance via l'email
        Partenaire_Shop partenaire_Shop = psr.findByEmail(tokenData.getEmail());
        if (partenaire_Shop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assurance non trouvée.");
        }

        // Activer l'assurance
        partenaire_Shop.setStatus(Status.ACTIF);
        psr.save(partenaire_Shop);

        // Supprimer le token
        ts.deleteToken(token);

        return ResponseEntity.status(HttpStatus.OK).body("Compte confirmé avec succès.");
    }

}
