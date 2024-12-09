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
import com.example.backend_ifc_foods.Repository.EmployeRepository;
import com.example.backend_ifc_foods.Repository.EntrepriseRepository;
import com.example.backend_ifc_foods.Repository.UtilisateurRepository;
import com.example.backend_ifc_foods.dto.AssuranceRequestDTO;
import com.example.backend_ifc_foods.dto.AssuranceResponseDTO;
import com.example.backend_ifc_foods.dto.EmployeRequestDTO;
import com.example.backend_ifc_foods.dto.EmployeResponseDTO;
import com.example.backend_ifc_foods.dto.EntrepriseRequestDTO;
import com.example.backend_ifc_foods.dto.EntrepriseResponseDTO;
import com.example.backend_ifc_foods.dto.OtpDataRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Assurance;
import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Entreprise;
import com.example.backend_ifc_foods.entite.Role;
import com.example.backend_ifc_foods.entite.Status;
import com.example.backend_ifc_foods.entite.Utilisateur;

@Service
@Transactional
public class Utilisateurserviceimpl implements UtilisateurService {

    private UtilisateurRepository utire;
    private EntrepriseRepository ersi;
    private EmployeRepository erty;
    private  AssuranceRepository assrr;

    @Autowired
    private OtpService otpService;

    @Autowired
    private TokenService tokenService;


    @Autowired
    private EmailService emailService;

  

    public Utilisateurserviceimpl(UtilisateurRepository utire, EntrepriseRepository ersi, EmployeRepository erty,
            AssuranceRepository assrr) {
        this.utire = utire;
        this.ersi = ersi;
        this.erty = erty;
        this.assrr = assrr;
      
    }

    @Override
    public  ResponseEntity<?> inscrip(EmployeRequestDTO ures) {
        if (utire.findByNomAndEmail(ures.getNom(), ures.getEmail()) == null) {
            Employee em = new Employee();
            em.setNom(ures.getNom());
            em.setTelephone(ures.getTelephone());
            em.setQuartier(ures.getQuartier());
            em.setVille(ures.getVille());
            em.setEmail(ures.getEmail());
            em.setPassword(ures.getPassword());
            em.setDate_inscription(new Date());
            em.setRole(Role.EMPLOYE);
            em.setStatus(Status.INACTIF);

            // Assignation de l'entreprise à l'employé
            long ide = ures.getId_entreprise();
            Entreprise ure = ersi.findById(ide).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise non trouvée"));
            em.setEntreprise(ure);
            utire.save(em);
            
            // Génération et envoi de l'OTP
             String otp = generateOtp();
             OtpDataRequestDTO p = new OtpDataRequestDTO(em.getEmail() , 4 , otp);
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
            erl.setDate_inscription(e.getDate_inscription());
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
    
    if (ure == null || ure.getStatus() == Status.INACTIF || ure.getStatus() == Status.EN_ATTENTE ) {
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
    public List<EntrepriseResponseDTO> inscriptent(EntrepriseRequestDTO enrdto , UtilisateurResponseDTO utilisateurConnecte) {
        List<EntrepriseResponseDTO> ent = new ArrayList<>();
        if(utire.findByNomAndEmail(enrdto.getNom(), enrdto.getEmail()) == null){
           Entreprise nt = new Entreprise();
           nt.setNom(enrdto.getNom());
           nt.setTelephone(enrdto.getTelephone());
           nt.setVille(enrdto.getVille());
           nt.setQuartier(enrdto.getQuartier());
           nt.setEmail(enrdto.getEmail());
           nt.setPassword(enrdto.getPassword());
           nt.setRole(Role.ENTREPRISE);
           nt.setStatus(Status.EN_ATTENTE);
           nt.setDate_inscription( new Date());
           nt.setDomaine_activite(enrdto.getDomaine_activite());

           Assurance assuran =assrr.findById(utilisateurConnecte.getId_utilisateur()).get();
           if(assuran == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "probleme interne");
           }
           nt.setAssurance(assuran);
           
           utire.save(nt);

         

        }else{
         EntrepriseResponseDTO et = new EntrepriseResponseDTO();
          String message = "l'entreprise " + enrdto.getNom() +  "existe deja";
          et.setErrormessage(message);
          ent.add(et);

        }
        if(!ent.isEmpty()){
            return ent;
        }



        return listentreprise();
    }

    @Override
    public List<EntrepriseResponseDTO> listentreprise() {
        List<Entreprise> etp =ersi.findAll();
        List<EntrepriseResponseDTO> hj = new ArrayList<>();
        for(Entreprise e : etp){
        EntrepriseResponseDTO etres =  new EntrepriseResponseDTO();
        etres.setId_utilisateur(e.getId_utilisateur());
        etres.setNom(e.getNom());
        etres.setTelephone(e.getTelephone());
        etres.setVille(e.getVille());
        etres.setQuartier(e.getQuartier());
        etres.setEmail(e.getEmail());
        etres.setPassword(e.getPassword());
        etres.setRoles(e.getRole());
        etres.setStatus(e.getStatus());
        etres.setDate_inscription(e.getDate_inscription());
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
    assurance.setDate_inscription(new Date());

    // Sauvegarder l'assurance temporairement
    Assurance savedAssurance = assrr.save(assurance);

    // Générer un token de confirmation
    String confirmationToken = UUID.randomUUID().toString();
    tokenService.saveToken(savedAssurance.getEmail(), confirmationToken);

    // Construire le lien de confirmation
    String confirmationUrl = "https://0337-102-244-45-236.ngrok-free.app/api/confirm?token=" + confirmationToken;

    // Envoyer l'email de confirmation
    String subject = "Confirmation de votre compte Assurance";
    String body = String.format(
            "Bonjour %s,\n\nMerci de vous être inscrit. Cliquez sur le lien ci-dessous pour confirmer votre compte :\n%s\n\nCordialement,\nL'équipe.",
            assurance.getNom(), confirmationUrl);
    emailService.sendEmail(assurance.getEmail(), subject, body);

    return ResponseEntity.status(HttpStatus.CREATED).body("Un email de confirmation a été envoyé.");
}


    @Override
    public List<AssuranceResponseDTO> listassurance() {
        List<Assurance> etp =assrr.findAll();
        List<AssuranceResponseDTO> hj = new ArrayList<>();
        for(Assurance e : etp){
            AssuranceResponseDTO etres =  new AssuranceResponseDTO();
            etres.setId_utilisateur(e.getId_utilisateur());
            etres.setNom(e.getNom());
            etres.setTelephone(e.getTelephone());
            etres.setVille(e.getVille());
            etres.setQuartier(e.getQuartier());
            etres.setEmail(e.getEmail());
            etres.setPassword(e.getPassword());
            etres.setRoles(e.getRole());
            etres.setStatus(e.getStatus());
            etres.setDate_inscription(e.getDate_inscription());
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

}
