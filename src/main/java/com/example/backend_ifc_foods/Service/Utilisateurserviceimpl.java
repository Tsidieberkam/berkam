package com.example.backend_ifc_foods.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_ifc_foods.Repository.UtilisateurRepository;
import com.example.backend_ifc_foods.dto.UtilisateurRequestDTO;
import com.example.backend_ifc_foods.dto.UtilisateurResponseDTO;
import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Role;
import com.example.backend_ifc_foods.entite.Utilisateur;

@Service
@Transactional
public class Utilisateurserviceimpl implements UtilisateurService {

    private UtilisateurRepository utire;

    public Utilisateurserviceimpl(UtilisateurRepository utire) {
        this.utire = utire;
    }

    @Override
    public List<UtilisateurResponseDTO> inscrip(List<UtilisateurRequestDTO> ures) {
        List<UtilisateurResponseDTO> smserror = new ArrayList<>();
        for (UtilisateurRequestDTO ur : ures) {
            if (utire.findByNomAndEmail(ur.getNom(), ur.getEmail()) == null) {
                Employee em = new Employee();
                em.setNom(ur.getNom());
                em.setTelephone(ur.getTelephone());
                em.setQuartier(ur.getQuartier());
                em.setVille(ur.getVille());
                em.setEmail(ur.getEmail());
                em.setPassword(ur.getPassword());
                em.setDate_inscription(new Date());
                em.setRole(Role.EMPLOYE);
                


                utire.save(em);

            } else {
                UtilisateurResponseDTO urp = new UtilisateurResponseDTO();
                urp.setNom(ur.getNom());
                urp.setEmail(ur.getEmail());
                urp.setErrormessage("utilisateur existe deja");
                smserror.add(urp);

            }

        }

        if (!smserror.isEmpty()) {
            return smserror;
        }
        return listuser();
    }





    @Override
    public List<UtilisateurResponseDTO> listuser() {
        // TODO Auto-generated method stub
        return null;
    }






    @Override
    public Utilisateur connexBoolean(UtilisateurRequestDTO ur) {
       
        Utilisateur ure = utire.findByEmailAndPassword(ur.getEmail(), ur.getPassword());
        if(ure == null){
          throw new ResponseStatusException(HttpStatus.NOT_FOUND , "connexion echouee");
        }
        return ure;
    }


}
