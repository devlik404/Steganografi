package com.baxdigital.steganoapp.repository;

import com.baxdigital.steganoapp.model.EncryptedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncryptedFileRepository extends JpaRepository<EncryptedFile, Long> {
}
