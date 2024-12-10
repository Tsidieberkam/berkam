package com.example.backend_ifc_foods.Repository;



import org.springframework.data.jpa.repository.JpaRepository;



import com.example.backend_ifc_foods.entite.Entreprise;


public interface EntrepriseRepository  extends JpaRepository<Entreprise ,Long>{

    public Entreprise findByEmail(String email);
   
    
    
}
