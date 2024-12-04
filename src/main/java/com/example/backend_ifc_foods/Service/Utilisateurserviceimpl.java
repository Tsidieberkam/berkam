package com.example.backend_ifc_foods.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
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

    public Utilisateurserviceimpl(UtilisateurRepository utire, EntrepriseRepository ersi, EmployeRepository erty ,AssuranceRepository assrr) {
        this.utire = utire;
        this.ersi = ersi;
        this.erty = erty;
        this.assrr= assrr;
    }

    @Override
    public List<EmployeResponseDTO> inscrip(EmployeRequestDTO ures) {
        List<EmployeResponseDTO> smserror = new ArrayList<>();

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
            em.setStatus(Status.EN_ATTENTE);

            // assignation de l'entreprise a l'employe
            long ide = ures.getId_entreprise();
            Entreprise ure = ersi.findById(ide).get();
            if (ure == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "entreprise non trouvee");
            }
            em.setEntreprise(ure);
            utire.save(em);

           

        } else {
            EmployeResponseDTO urp = new EmployeResponseDTO();
            urp.setNom(ures.getNom());
            urp.setEmail(ures.getEmail());
            urp.setErrormessage("utilisateur existe deja");
            smserror.add(urp);

        }

        if (!smserror.isEmpty()) {
            return smserror;
        }
        return listemploye();
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
    
            // Gestion des relations complexes (ex. : `Compte`, `Entreprise`)
            if (e.getCompte() != null) {
                erl.setIdcop(e.getCompte().getNumero_compte()); // par exemple, si vous voulez juste le numéro de compte
            } 
    
            if (e.getEntreprise() != null) {
                erl.setNom_entreprise(e.getEntreprise().getNom()); // Ou d'autres informations de l'entreprise
            }
    
            // Ajout du DTO dans la liste finale
            allemp.add(erl);
        }
    
        return allemp;
    }
    

    @Override
    public UtilisateurResponseDTO connexBoolean(UtilisateurRequestDTO ur) {

        Utilisateur ure = utire.findByEmailAndPassword(ur.getEmail(), ur.getPassword());
    
    if (ure == null) {
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


           List<Assurance> asus = new ArrayList<>();
           Assurance assuran =assrr.findById(utilisateurConnecte.getId_utilisateur()).get();
           if(assuran == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "probleme interne");
           }
           asus.add(assuran);
           nt.setAssurances(asus);
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
        etres.setAssurances(e.getAssurances());

        hj.add(etres);
        
        }

        return hj;
    }

    @Override
    public List<AssuranceResponseDTO> inscriptass(AssuranceRequestDTO assuranceRequestDTO) {
        List<AssuranceResponseDTO> asst = new ArrayList<>();
        if(utire.findByNomAndEmail(assuranceRequestDTO.getNom(), assuranceRequestDTO.getEmail()) == null){
            Assurance nt = new Assurance();
            nt.setCode_ifc(assuranceRequestDTO.getCode_ifc());
            nt.setNom(assuranceRequestDTO.getNom());
            nt.setTelephone(assuranceRequestDTO.getTelephone());
            nt.setVille(assuranceRequestDTO.getVille());
            nt.setQuartier(assuranceRequestDTO.getQuartier());
            nt.setEmail(assuranceRequestDTO.getEmail());
            nt.setPassword(assuranceRequestDTO.getPassword());
            nt.setRole(Role.ASSURANCE);
            nt.setStatus(Status.EN_ATTENTE);
            nt.setDate_inscription( new Date());
            
  
 
            utire.save(nt);
 
         }else{
           AssuranceResponseDTO et = new AssuranceResponseDTO();
           String message = "l'assurance" + assuranceRequestDTO.getNom() +  "existe deja";
           et.setErrormessage(message);
           asst.add(et);
 
         }
         if(!asst.isEmpty()){
             return asst;
         }
 
        return listassurance();
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

}
