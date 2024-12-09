package com.example.backend_ifc_foods.Service;


import org.springframework.http.ResponseEntity;

import com.example.backend_ifc_foods.dto.OtpDataRequestDTO;

public interface OtpService {
    public void saveOtp( OtpDataRequestDTO tpre);
    public  ResponseEntity<?> verifyOtp(OtpDataRequestDTO py);
    
}
