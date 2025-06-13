package com.baxdigital.steganoapp.repository;

import com.baxdigital.steganoapp.model.DecryptFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DecryptFileRepository extends JpaRepository<DecryptFile, Long> {
}
