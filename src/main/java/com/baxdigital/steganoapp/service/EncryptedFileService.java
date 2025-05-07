package com.baxdigital.steganoapp.service;


import com.baxdigital.steganoapp.model.EncryptedFile;
import com.baxdigital.steganoapp.repository.EncryptedFileRepository;
import com.baxdigital.steganoapp.util.FileEncryptor;
import com.baxdigital.steganoapp.util.SteganographyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EncryptedFileService {

    @Autowired
    private EncryptedFileRepository repository;


    private final String uploaddir = "uploads/";

    public void saveMetaData(MultipartFile file,MultipartFile image,String password,String originalName, String encryptedName, String stegoImageName, long size, String type) throws Exception{
        byte[] encryptedData = FileEncryptor.encrypt(file.getBytes(), password);

        // 2. Sisipkan ke gambar
        BufferedImage originalImage = ImageIO.read(image.getInputStream());
        BufferedImage stegoImage = SteganographyUtil.embedLSB(encryptedData, originalImage);
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int maxCapacity = width * height * 3 / 8; // max byte yang bisa disimpan
        if (file.getSize() > maxCapacity) {
            throw new IllegalArgumentException("Ukuran file terlalu besar untuk gambar ini!");
        }


        // 3. Simpan gambar hasil stego
        String stegoFileName = UUID.randomUUID() + "_stego.png";
        File outputImage = new File(uploaddir + stegoFileName);
        ImageIO.write(stegoImage, "png", outputImage);

        // 4. Simpan metadata ke database
        EncryptedFile encryptedFile = new EncryptedFile();
        encryptedFile.setOriginalFileName(file.getOriginalFilename());
        encryptedFile.setEncryptedFileName(file.getOriginalFilename() + ".enc");
        encryptedFile.setStegoImageFileName(stegoFileName);
        encryptedFile.setFileSize(file.getSize());
        encryptedFile.setFileType(file.getContentType());
        encryptedFile.setUploadDate(LocalDateTime.now());

        repository.save(encryptedFile);


    }
}
