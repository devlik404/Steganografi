package com.baxdigital.steganoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "encrypted_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long encrypt_id;

    private int user_id;

    @Column(name = "encrypted_file_name")
    private String encryptedFileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stego_image_file_name")
    private String stegoImageFileName;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "encryption_key")
    private String encryptionKey;

}
