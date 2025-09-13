package com.baxdigital.steganoapp.repository;

import com.baxdigital.steganoapp.model.FileEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface getFileRepository extends JpaRepository<FileEntitiy,Long> {
}
