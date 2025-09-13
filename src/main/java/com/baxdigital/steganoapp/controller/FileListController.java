package com.baxdigital.steganoapp.controller;


import com.baxdigital.steganoapp.model.FileEntitiy;
import com.baxdigital.steganoapp.service.FileServices;
import com.baxdigital.steganoapp.util.DTOFiles;

import com.baxdigital.steganoapp.util.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/files")
@Controller
public class FileListController {
    @Autowired
    private FileServices services;


    @GetMapping
    public FileResponse getAllFiles(){
        return services.getFileAll();

    }

    //GET File ById
    @GetMapping("/{id}")
    public FileEntitiy getFileById(@PathVariable Long id){

        return services.getFileById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFileById(@PathVariable Long id){
        boolean delted = services.deleteFileById(id);
        if(delted){
            return "File with ID " + id + " deleted successfully.";
        }else {
            return "File with ID " + id + " not found.";
        }
    }
}
