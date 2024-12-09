package com.example.backend_ifc_foods.entite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OtpData  {
    private String email;
    private long expirationTime;
    
}
