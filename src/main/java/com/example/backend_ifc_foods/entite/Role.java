package com.example.backend_ifc_foods.entite;


public enum Role {
    ADMIN("Administrateur"),  
    EMPLOYE("Employe"),      
    ASSURANCE("Assurance"),  
    ENTREPRISE("Entreprise"), 
    SHOP("Shop");   

    private final String description;
    Role(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
