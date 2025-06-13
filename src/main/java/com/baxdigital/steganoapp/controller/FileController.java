package com.baxdigital.steganoapp.controller;

import com.baxdigital.steganoapp.service.EncryptedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private EncryptedFileService fileService;

    @PostMapping("/save-metadata")
    public ResponseEntity saveMetadata(
            @RequestParam MultipartFile file,
            @RequestParam MultipartFile image,
            @RequestParam String password) {
        try {
      
           fileService.saveMetaData(file, image, password);
            return ResponseEntity.ok("Upload dan proses berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gagal memproses file: " + e.getMessage());
        }
    }
}
