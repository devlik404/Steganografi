package com.baxdigital.steganoapp.service;

import com.baxdigital.steganoapp.model.UsersModel;
import com.baxdigital.steganoapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersModel saveMetaData(String userName, String Email, String password) {
        UsersModel usersData = UsersModel.builder().userName(userName).email(Email).password(password).build();
        return  usersRepository.save(usersData);
    }
}
