package com.baxdigital.steganoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "decrypt_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long decrypt_id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "decrypted_file_name")
    private String decryptedFileName;

    @Column(name = "encrypted_file_name")
    private String encryptedFileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
