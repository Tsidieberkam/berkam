package com.example.backend_ifc_foods.entite;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_utilisateur;
    @Column(name="nom")
    private String nom;
    @Column(name="telephone" , unique =true)
    private String telephone;
    @Column(name="quartier")
    private String quartier;
    @Column(name="ville")
    private String ville;
    @Column(name="email" , unique = true)
    private String email;
    @Column(name="motdepasse")
    private String password;
    @Column(name="date_inscription")
    private Date date_inscription;
    @Enumerated(EnumType.STRING) 
    private Role role;
    @Enumerated(EnumType.STRING) 
    private Status status;
    @Transient
    private String errormessage;
    
}
