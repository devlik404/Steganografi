package com.baxdigital.steganoapp.controller;

import com.baxdigital.steganoapp.service.extractAndDecrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class decryptController {

    @Autowired
    private extractAndDecrypt extractAndDecrypt;

    @PostMapping("/extract")
    public ResponseEntity<?> extractAndDecryptFile(
            @RequestParam MultipartFile image,
            @RequestParam String password) {
        try {
        
            extractAndDecrypt.extractAndDecryptService(image, password);
            
            return ResponseEntity.ok("success extract");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mendekripsi: " + e.getMessage());
        }
    }

}
