package com.baxdigital.steganoapp.controller;


import com.baxdigital.steganoapp.model.UsersModel;
import com.baxdigital.steganoapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/steganografi")
public class UsersController {
    @Autowired
    private UsersService users;

    @PostMapping("/register-user")
    public UsersModel saveMetaData(@RequestParam String username,@RequestParam String email, @RequestParam String password) {
        return users.saveMetaData(username, email, password);
    }
    
}
