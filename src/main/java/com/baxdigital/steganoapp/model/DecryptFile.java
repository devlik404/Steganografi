package com.baxdigital.steganoapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="decryptFile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private String fileName;
    private String encryptedFileName;
    private String decryptedFileName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
