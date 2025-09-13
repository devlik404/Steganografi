package com.baxdigital.steganoapp.service;

import com.baxdigital.steganoapp.model.DecryptFile;
import com.baxdigital.steganoapp.model.FileEntitiy;
import com.baxdigital.steganoapp.repository.DecryptFileRepository;
import com.baxdigital.steganoapp.repository.getFileRepository;
import com.baxdigital.steganoapp.util.DTOFiles;
import com.baxdigital.steganoapp.util.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServices {

    @Autowired
    private getFileRepository repository;
    @Autowired
    private DecryptFileRepository repositoryDecrypt;


    public FileResponse getFileAll() {
        List<FileEntitiy> data = repository.findAll();
        List<DecryptFile> dataDecrypt = repositoryDecrypt.findAll();

        List<DTOFiles> files = data.stream()
                .map(file -> new DTOFiles(
                        file.getEncrypt_id(),
                        file.getEncryptedFileName(),
                        file.getFileSize().intValue(),
                        file.getStegoImageFileName(),
                        file.getUploadDate().toLocalDate()
                ))
                .toList();

        return new FileResponse(data.size(),dataDecrypt.size(), files);
    }


    public FileEntitiy getFileById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Boolean deleteFileById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
