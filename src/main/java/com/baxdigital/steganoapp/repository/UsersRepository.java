package com.baxdigital.steganoapp.repository;

import com.baxdigital.steganoapp.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersModel, Long> {
}
