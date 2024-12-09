package com.example.backend_ifc_foods.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_ifc_foods.Repository.EmployeRepository;
import com.example.backend_ifc_foods.Repository.UtilisateurRepository;
import com.example.backend_ifc_foods.dto.OtpDataRequestDTO;
import com.example.backend_ifc_foods.entite.Employee;
import com.example.backend_ifc_foods.entite.Entreprise;
import com.example.backend_ifc_foods.entite.OtpData;
import com.example.backend_ifc_foods.entite.Status;
import com.example.backend_ifc_foods.entite.Utilisateur;

@Service
@Transactional
public class OtpServiceImpl implements OtpService  {

    private final Map<String, OtpData> otpStorage = new HashMap<>();

    @Autowired
    private EmailService emailService;

    private EmployeRepository erty;
    private UtilisateurRepository utire;

    public OtpServiceImpl(EmployeRepository erty, UtilisateurRepository utire) {
        this.erty = erty;
        this.utire = utire;
    }

    @Override
    public void saveOtp(OtpDataRequestDTO tpre) {
        OtpData otpData = new OtpData(tpre.getEmail(),
                System.currentTimeMillis() + tpre.getExpirationTime() * 60 * 1000);
        otpStorage.put(tpre.getOtp(), otpData); // Associer l'OTP à l'email
    }

    @Override
    public ResponseEntity<?> verifyOtp(OtpDataRequestDTO py) {
        // Récupérer les données associées à l'OTP
        OtpData otpData = getOtpData(py.getOtp());
    
        // Vérifier si l'OTP est valide
        if (otpData == null || !validateOtp(py.getOtp())) {
            // Si les données OTP sont inexistantes ou l'OTP est invalide/expiré
            List<Employee> inactiveEmployees = erty.findByStatus(Status.INACTIF);
    
            if (inactiveEmployees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun employé inactif trouvé.");
            }

            erty.deleteAll(inactiveEmployees);

            return ResponseEntity.status(HttpStatus.OK).body(inactiveEmployees.size() + "l'otp invalide ou le temps a expire");
        }
    
        // Si OTP valide, récupérer l'utilisateur
        Employee em = erty.findByEmail(otpData.getEmail());
    
        // Vérifier si l'utilisateur existe et si son statut est INACTIF
        if (em != null && em.getStatus() == Status.INACTIF) {
            // Mettre à jour le statut de l'utilisateur
            em.setStatus(Status.EN_ATTENTE); // Statut pour indiquer que l'email est vérifié
            erty.save(em);
    
            // Envoyer un email à l'entreprise pour activer le compte
            Entreprise entreprise = em.getEntreprise();
            if (entreprise != null) {
                String entrepriseEmail = entreprise.getEmail();
                String subject = "Activation du compte employé requise";
                String body = String.format(
                    "Bonjour %s,\n\nLe compte de votre employé %s (%s) a été vérifié avec succès. "
                            + "Veuillez activer son compte via votre interface.\n\nCordialement,\nL'équipe EasyFood.",
                    entreprise.getNom(), em.getNom(), em.getEmail()
                );
    
                emailService.sendEmail(entrepriseEmail, subject, body);
    
                return ResponseEntity.status(HttpStatus.OK)
                        .body("OTP validé avec succès. Un email a été envoyé à l'entreprise pour activer le compte.");
            } else {
                // Si l'entreprise associée est introuvable
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Entreprise associée introuvable.");
            }
        }
    
        // Si l'utilisateur est déjà actif ou introuvable
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Utilisateur non trouvé ou déjà actif.");
    }

    public OtpData getOtpData(String otp) {
        return otpStorage.get(otp);
    }

    public boolean validateOtp(String otp) {
        OtpData otpData = otpStorage.get(otp);
        return otpData != null && System.currentTimeMillis() <= otpData.getExpirationTime();
    }

}
