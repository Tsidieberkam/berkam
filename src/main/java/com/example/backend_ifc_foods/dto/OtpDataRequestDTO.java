package com.example.backend_ifc_foods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpDataRequestDTO {
    private String email;
    private long expirationTime;
    private String otp;
    
}
