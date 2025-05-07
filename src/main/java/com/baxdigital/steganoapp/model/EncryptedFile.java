package com.baxdigital.steganoapp.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="encrypted_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncryptedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String originalFileName;
    private String encryptedFileName;
    private String stegoImageFileName;

    private Long fileSize;
    private String fileType;
    private  String password;

    private LocalDateTime uploadDate;

    //geter and setter
    public long getId(long id) {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalFileNames() {
         return originalFileName;
    }
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
    public String getEncryptedFileNames() {
        return encryptedFileName;
    }
    public void setEncryptedFileName(String encryptedFileName) {
        this.encryptedFileName = encryptedFileName;
    }

    public String getStegoImageFileNames() {
        return stegoImageFileName;
    }
    public void setStegoImageFileName(String stegoImageFileName) {
        this.stegoImageFileName = stegoImageFileName;
    }
    public Long getFileSizes() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileTypes() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public LocalDateTime getUploadDates() {
        return uploadDate;
    }
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }






}
