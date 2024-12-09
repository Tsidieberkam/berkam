package com.example.backend_ifc_foods.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend_ifc_foods.Repository.TokenRepository;
import com.example.backend_ifc_foods.entite.ConfirmationToken;


@Service
@Transactional
public class TokenService  {
    @Autowired
    private TokenRepository tokenRepository;

    public void saveToken(String email, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setEmail(email);
        confirmationToken.setExpirationDate(new Date(System.currentTimeMillis() + 3600 * 1000)); // Expire après 1 heure
        tokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getTokenData(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token);
        if (confirmationToken == null || confirmationToken.getExpirationDate().before(new Date())) {
            return null;
        }
        return confirmationToken;
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}
