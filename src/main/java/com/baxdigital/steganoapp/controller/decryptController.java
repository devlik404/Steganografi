package com.baxdigital.steganoapp.controller;

import com.baxdigital.steganoapp.service.extractAndDecrypt;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class decryptController {

    @Autowired
    private extractAndDecrypt extractAndDecrypt;

    @PostMapping("/extract")
    public ResponseEntity<?> extractAndDecryptFile(
            @RequestParam MultipartFile image,
            @RequestParam String password
            ) {
        try {

            String tempFilename = extractAndDecrypt.extractAndDecryptService(image, password);
            String fileUrl = "/preview/" + tempFilename;
            return ResponseEntity.ok().body(Map.of("previewUrl", fileUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal mendekripsi: " + e.getMessage());
        }
    }

}
