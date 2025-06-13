package com.baxdigital.steganoapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="decrypt_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long user_id;
    private String file_name;
    private String decrypted_file_name;
    private String encrypted_file_name;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getuser_id() {
        return user_id;
    }

    public void setuser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFileName() {
        return file_name;
    }
    public void setfile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDecryptedFileName() {
        return decrypted_file_name;
    }
    public void setdecrypted_file_name(String decrypted_file_name) {
        this.decrypted_file_name = decrypted_file_name;
    }

    public String getEncryptedFileName() {
        return encrypted_file_name;
    }

    public void setencrypted_file_name(String encrypted_file_name) {

        this.encrypted_file_name = encrypted_file_name;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
