package com.example.backend_ifc_foods.entite;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateur_roles",joinColumns = @JoinColumn(name ="id_utilisateur"),inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "numero_compte")
    private Compte compte;
    
}