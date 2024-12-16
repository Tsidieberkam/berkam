package com.example.backend_ifc_foods.Service;

import java.security.SecureRandom;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_ifc_foods.Repository.AssuranceRepository;
import com.example.backend_ifc_foods.Repository.CompteRepository;
import com.example.backend_ifc_foods.Repository.EmployeRepository;
import com.example.backend_ifc_foods.Repository.EntrepriseRepository;
import com.example.backend_ifc_foods.Repository.Partenaire_ShopRepository;
import com.example.backend_ifc_foods.Repository.UtilisateurRepository;
import com.example.backend_ifc_foods.dto.AssuranceRequestDTO;
import com.example.backend_ifc_foods.dto.AssuranceResponseDTO;
import com.example.backend_ifc_foods.dto.CompteRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeResponseDTO;
import com.example.backend_ifc_foods.dto.EntrepriseRequestDTO;
import com.example.backend_ifc_foods.dto.EntrepriseResponseDTO;
import com.example.backend_ifc_foods.dto.OtpDataRequestDTO;
import com.example.backend_ifc_foods.dto.Partenaire_ShopRequestDTO;
import com.example.backend_ifc_foods.dto.Partenaire_ShopResponseDTO;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Assurance;
import com.example.backend_ifc_foods.entite.Compte_Credit;
import com.example.backend_ifc_foods.entite.Compte_Entreprise;
import com.example.backend_ifc_foods.entite.Compte_Partenaite_Shop;
import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Entreprise;
import com.example.backend_ifc_foods.entite.Partenaire_Shop;
import com.example.backend_ifc_foods.entite.Role;
import com.example.backend_ifc_foods.entite.Status;
import com.example.backend_ifc_foods.entite.Utilisateur;

@Service
@Transactional
public class Utilisateurserviceimpl implements UtilisateurService {

    private UtilisateurRepository utire;
    private EntrepriseRepository ersi;
    private EmployeRepository erty;
    private AssuranceRepository assrr;
    private Partenaire_ShopRepository psss;
    private  CompteRepository compteCreditRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    public Utilisateurserviceimpl(UtilisateurRepository utire, EntrepriseRepository ersi, EmployeRepository erty,
            AssuranceRepository assrr, Partenaire_ShopRepository psss , CompteRepository compteCreditRepository) {
        this.utire = utire;
        this.ersi = ersi;
        this.erty = erty;
        this.assrr = assrr;
        this.psss = psss;
        this.compteCreditRepository=compteCreditRepository;

    }

    @Override
    public ResponseEntity<?> inscrip(EmployeRequestDTO ures) {
        if (utire.findByNomAndEmail(ures.getNom(), ures.getEmail()) == null) {
            Employee em = new Employee();
            em.setNom(ures.getNom());
            em.setTelephone(ures.getTelephone());
            em.setQuartier(ures.getQuartier());
            em.setVille(ures.getVille());
            em.setEmail(ures.getEmail());
            em.setPassword(ures.getPassword());
            em.setDateinscription(new Date());
            em.setRole(Role.EMPLOYE);
            em.setStatus(Status.INACTIF);

            // Assignation de l'entreprise à l'employé
            long ide = ures.getId_entreprise();
            Entreprise ure = ersi.findById(ide)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise non trouvée"));
            em.setEntreprise(ure);
            utire.save(em);

            // Génération et envoi de l'OTP
            String otp = generateOtp();
            OtpDataRequestDTO p = new OtpDataRequestDTO(em.getEmail(), 15, otp);
            otpService.saveOtp(p);
            String subject = "Vérification de votre email";
            String body = "Votre code de vérification est : " + otp;
            emailService.sendEmail(em.getEmail(), subject, body);

            // Retour d'une réponse avec statut CREATED
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Code de vérification envoyé à l'adresse email : " + em.getEmail());

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Un utilisateur  existe déjà.");

        }

    }

    @Override
    public List<EmployeResponseDTO> listemploye() {
        // Récupération de tous les employés
        List<Employee> allem = erty.findAll();
        List<EmployeResponseDTO> allemp = new ArrayList<>();

        // Boucle sur chaque employé pour le mapper au DTO
        for (Employee e : allem) {
            EmployeResponseDTO erl = new EmployeResponseDTO();

            // Mapping des propriétés simples
            erl.setId_utilisateur(e.getId_utilisateur());
            erl.setNom(e.getNom());
            erl.setTelephone(e.getTelephone());
            erl.setEmail(e.getEmail());
            erl.setPassword(e.getPassword());
            erl.setVille(e.getVille());
            erl.setQuartier(e.getQuartier());
            erl.setDate_inscription(e.getDateinscription());
            erl.setRoles(e.getRole());
            erl.setStatus(e.getStatus());
            erl.setEntreprise(e.getEntreprise());

            allemp.add(erl);
        }

        return allemp;
    }

    @Override
    public UtilisateurResponseDTO connexBoolean(UtilisateurRequestDTO ur) {

        Utilisateur ure = utire.findByEmailAndPassword(ur.getEmail(), ur.getPassword());

        if (ure == null || ure.getStatus() == Status.INACTIF || ure.getStatus() == Status.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Connexion échouée");
        }

        // Mapper les informations de l'utilisateur à un DTO de réponse
        UtilisateurResponseDTO responseDTO = new UtilisateurResponseDTO();
        responseDTO.setId_utilisateur(ure.getId_utilisateur());
        responseDTO.setNom(ure.getNom());
        responseDTO.setTelephone(ure.getTelephone());
        responseDTO.setEmail(ure.getEmail());
        responseDTO.setRoles(ure.getRole());

        System.out.println("Connexion réussie");

        return responseDTO;

    }

    @Override
    public ResponseEntity<?> inscriptent(EntrepriseRequestDTO enrdto, UtilisateurResponseDTO utilisateurConnecte) {
        // Vérifier si l'entreprise existe déjà
        if (utire.findByNomAndEmail(enrdto.getNom(), enrdto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("L'entreprise " + enrdto.getNom() + " existe déjà.");
        }

        // Créer une nouvelle entreprise avec un statut "EN_ATTENTE"
        Entreprise entreprise = new Entreprise();
        entreprise.setNom(enrdto.getNom());
        entreprise.setTelephone(enrdto.getTelephone());
        entreprise.setVille(enrdto.getVille());
        entreprise.setQuartier(enrdto.getQuartier());
        entreprise.setEmail(enrdto.getEmail());
        entreprise.setPassword(enrdto.getPassword());
        entreprise.setRole(Role.ENTREPRISE);
        entreprise.setStatus(Status.EN_ATTENTE);
        entreprise.setDateinscription(new Date());
        entreprise.setDomaine_activite(enrdto.getDomaine_activite());

        // Récupérer l'assurance associée
        Assurance assurance = assrr.findById(utilisateurConnecte.getId_utilisateur()).orElse(null);
        if (assurance == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assurance non trouvée.");
        }
        entreprise.setAssurance(assurance);

        Entreprise savedAssurance = ersi.save(entreprise);

        // Générer un token de confirmation
        String confirmationToken = UUID.randomUUID().toString();
        tokenService.saveToken(savedAssurance.getEmail(), confirmationToken);

        // Construire le lien de confirmation
        String confirmationUrl = "https://ad5f-102-244-45-248.ngrok-free.app/api/confirm/entreprise?token="
                + confirmationToken;

        // Construire le contenu de l'email
        String subject = "Confirmation de votre inscription en tant qu'entreprise";
        String body = String.format(
                "Bonjour %s,\n\nVotre inscription a été initiée par l'assurance '%s'. "
                        + "Veuillez confirmer votre compte en cliquant sur le lien ci-dessous :\n%s\n\nCordialement,\n%s.",
                entreprise.getNom(),
                assurance.getNom(), // Nom de l'assurance
                confirmationUrl,
                assurance.getNom()); // Assurance comme signataire

        // Envoyer l'email
        emailService.sendEmail(entreprise.getEmail(), subject, body);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Un email de confirmation a été envoyé à l'entreprise.");
    }

    @Override
    public List<EntrepriseResponseDTO> listentreprise() {
        List<Entreprise> etp = ersi.findAll();
        List<EntrepriseResponseDTO> hj = new ArrayList<>();
        for (Entreprise e : etp) {
            EntrepriseResponseDTO etres = new EntrepriseResponseDTO();
            etres.setId_utilisateur(e.getId_utilisateur());
            etres.setNom(e.getNom());
            etres.setTelephone(e.getTelephone());
            etres.setVille(e.getVille());
            etres.setQuartier(e.getQuartier());
            etres.setEmail(e.getEmail());
            etres.setPassword(e.getPassword());
            etres.setRoles(e.getRole());
            etres.setStatus(e.getStatus());
            etres.setDate_inscription(e.getDateinscription());
            etres.setDomaine_activite(e.getDomaine_activite());
            etres.setAssurance(e.getAssurance());

            hj.add(etres);

        }

        return hj;
    }

    @Override
    public ResponseEntity<?> inscriptass(AssuranceRequestDTO assuranceRequestDTO) {
        // Vérifier si l'assurance existe déjà
        if (utire.findByNomAndEmail(assuranceRequestDTO.getNom(), assuranceRequestDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cette assurance existe déjà.");
        }

        // Créer une nouvelle assurance avec statut INACTIF
        Assurance assurance = new Assurance();
        assurance.setCode_ifc(assuranceRequestDTO.getCode_ifc());
        assurance.setNom(assuranceRequestDTO.getNom());
        assurance.setTelephone(assuranceRequestDTO.getTelephone());
        assurance.setVille(assuranceRequestDTO.getVille());
        assurance.setQuartier(assuranceRequestDTO.getQuartier());
        assurance.setEmail(assuranceRequestDTO.getEmail());
        assurance.setPassword(assuranceRequestDTO.getPassword());
        assurance.setRole(Role.ASSURANCE);
        assurance.setStatus(Status.INACTIF);
        assurance.setDateinscription(new Date());

        // Sauvegarder l'assurance temporairement
        Assurance savedAssurance = assrr.save(assurance);

        // Générer un token de confirmation
        String confirmationToken = UUID.randomUUID().toString();
        tokenService.saveToken(savedAssurance.getEmail(), confirmationToken);

        // Construire le lien de confirmation
        String confirmationUrl = "https://ad5f-102-244-45-248.ngrok-free.app/api/confirm?token=" + confirmationToken;

        // Envoyer l'email de confirmation
        String subject = "Confirmation de votre compte Assurance";
        String body = String.format(
                "Bonjour %s,\n\nMerci de vous être inscrit. Cliquez sur le lien ci-dessous pour confirmer votre compte :\n%s\n\nCordialement,\nL'équipe EasyFoods.",
                assurance.getNom(), confirmationUrl);
        emailService.sendEmail(assurance.getEmail(), subject, body);

        return ResponseEntity.status(HttpStatus.CREATED).body("Un email de confirmation a été envoyé.");
    }

    @Override
    public List<AssuranceResponseDTO> listassurance() {
        List<Assurance> etp = assrr.findAll();
        List<AssuranceResponseDTO> hj = new ArrayList<>();
        for (Assurance e : etp) {
            AssuranceResponseDTO etres = new AssuranceResponseDTO();
            etres.setId_utilisateur(e.getId_utilisateur());
            etres.setNom(e.getNom());
            etres.setTelephone(e.getTelephone());
            etres.setVille(e.getVille());
            etres.setQuartier(e.getQuartier());
            etres.setEmail(e.getEmail());
            etres.setPassword(e.getPassword());
            etres.setRoles(e.getRole());
            etres.setStatus(e.getStatus());
            etres.setDate_inscription(e.getDateinscription());
            etres.setCode_ifc(e.getCode_ifc());

            hj.add(etres);

        }

        return hj;
    }

    @Override
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(otp);
    }

    @Override
    public ResponseEntity<?> insparr(Partenaire_ShopRequestDTO partenaire_ShopRequestDTO) {
        if (utire.findByNomAndEmail(partenaire_ShopRequestDTO.getNom(), partenaire_ShopRequestDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce Shop existe déjà.");
        }

        // Créer une nouvelle assurance avec statut INACTIF
        Partenaire_Shop partenaire_Shop1 = new Partenaire_Shop();
        partenaire_Shop1.setDomaine(partenaire_ShopRequestDTO.getDomaine());
        partenaire_Shop1.setNom(partenaire_ShopRequestDTO.getNom());
        partenaire_Shop1.setTelephone(partenaire_ShopRequestDTO.getTelephone());
        partenaire_Shop1.setVille(partenaire_ShopRequestDTO.getVille());
        partenaire_Shop1.setQuartier(partenaire_ShopRequestDTO.getQuartier());
        partenaire_Shop1.setEmail(partenaire_ShopRequestDTO.getEmail());
        partenaire_Shop1.setPassword(partenaire_ShopRequestDTO.getPassword());
        partenaire_Shop1.setRole(Role.SHOP);
        partenaire_Shop1.setStatus(Status.EN_ATTENTE);
        partenaire_Shop1.setDateinscription(new Date());

        // Sauvegarder l'assurance temporairement
        Partenaire_Shop savedpPartenaire_Shop = psss.save(partenaire_Shop1);

        // Générer un token de confirmation
        String confirmationToken = UUID.randomUUID().toString();
        tokenService.saveToken(savedpPartenaire_Shop.getEmail(), confirmationToken);

        // Construire le lien de confirmation
        String confirmationUrl = "https://df25-102-244-45-248.ngrok-free.app/api/confirm/partenaire_shop?token="
                + confirmationToken;

        // Envoyer l'email de confirmation
        String subject = "Confirmation de votre compte Shop";
        String body = String.format(
                "Bonjour %s,\n\nMerci de vous être inscrit. Cliquez sur le lien ci-dessous pour confirmer votre compte :\n%s\n\nCordialement,\nL'équipe EasyFoods.",
                partenaire_Shop1.getNom(), confirmationUrl);
        emailService.sendEmail(partenaire_Shop1.getEmail(), subject, body);

        return ResponseEntity.status(HttpStatus.CREATED).body("Un email de confirmation a été envoyé.");
    }

    @Override
    public List<Partenaire_ShopResponseDTO> listass() {

        List<Partenaire_Shop> asus = psss.findAll();
        List<Partenaire_ShopResponseDTO> psr = new ArrayList<>();

        for (Partenaire_Shop p : asus) {

            Partenaire_ShopResponseDTO pes = new Partenaire_ShopResponseDTO();
            pes.setId_utilisateur(p.getId_utilisateur());
            pes.setNom(p.getNom());
            pes.setQuartier(p.getQuartier());
            pes.setVille(p.getVille());
            pes.setDate_inscription(p.getDateinscription());
            pes.setEmail(p.getEmail());
            pes.setRoles(p.getRole());
            pes.setStatus(p.getStatus());
            pes.setTelephone(p.getTelephone());
            pes.setDomaine(p.getDomaine());

            psr.add(pes);

        }

        return psr;
    }

    @Override
    public ResponseEntity<?> creercompte(UtilisateurResponseDTO ur, CompteRequestDTO up) {
    
        // Récupérer l'utilisateur par son ID
        Utilisateur utilisateur = ersi.findById(ur.getId_utilisateur())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur non trouvé"));
    
        // Vérification du type d'utilisateur
        if (utilisateur instanceof Employee) {
            // Création d'un compte crédit
            Compte_Credit compteCredit = new Compte_Credit();
            compteCredit.setDate_creation(new Date());
            compteCredit.setUtilisateur(utilisateur);
    
            // Sauvegarde du compte crédit
            compteCreditRepository.save(compteCredit);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Compte crédit créé avec succès pour l'utilisateur : " + utilisateur.getNom());
    
        } else if (utilisateur instanceof Entreprise) {
            // Création d'un compte entreprise
            Compte_Entreprise compteEntreprise = new Compte_Entreprise();
            compteEntreprise.setDate_creation(new Date());
            compteEntreprise.setSolde(up.getSolde());
            compteEntreprise.setUtilisateur(utilisateur);
    
            // Sauvegarde du compte entreprise
            compteCreditRepository.save(compteEntreprise);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Compte entreprise créé avec succès pour l'entreprise : " + utilisateur.getNom());
    
        } else if (utilisateur instanceof Partenaire_Shop) {
            // Création d'un compte partenariat shop
            Compte_Partenaite_Shop comptePartenaireShop = new Compte_Partenaite_Shop();
            comptePartenaireShop.setDate_creation(new Date());
            comptePartenaireShop.setUtilisateur(utilisateur);
    
            // Sauvegarde du compte partenariat shop
            compteCreditRepository.save(comptePartenaireShop);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Compte partenaire créé avec succès pour le partenaire : " + utilisateur.getNom());
    
        } else {
            // Si le type d'utilisateur est inconnu
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Type d'utilisateur non reconnu pour la création d'un compte.");
        }
    }
    

}
