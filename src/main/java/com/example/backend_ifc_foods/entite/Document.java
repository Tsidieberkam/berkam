package com.example.backend_ifc_foods.entite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_document;
    @Column(name="nom_doc")
    private String nom_document;
    @Lob  // Utilisation de @Lob pour indiquer une colonne de type CLOB (large object)
    @Column(name = "contenu", length = 80000)  // Ajuster la taille selon vos besoins
    private String contenu;
    @Transient
    private String errormessage;
}
