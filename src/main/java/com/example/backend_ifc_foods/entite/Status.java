package com.example.backend_ifc_foods.entite;

public enum Status {
    ACTIF("Actif"),  
    INACTIF("Inactif"),      
    EN_ATTENTE("En_Attente");  
 

    private final String description;

    Status(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
