package com.baxdigital.steganoapp.service;

import com.baxdigital.steganoapp.model.DecryptFile;
import com.baxdigital.steganoapp.repository.DecryptFileRepository;
import com.baxdigital.steganoapp.util.FileEncryptor;
import com.baxdigital.steganoapp.util.SteganographyUtil;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class extractAndDecrypt {

    @Autowired
    private DecryptFileRepository repository;

    private final String uploaddir = "extract/";

    public String extractAndDecryptService(MultipartFile imageFile, String password)
            throws Exception {

            BufferedImage stegoImage = ImageIO.read(imageFile.getInputStream());

            // 1. Ekstrak semua data dari gambar
            byte[] fullData = SteganographyUtil.extractLSB(stegoImage);

            // 2. Ambil panjang nama file (4 byte pertama)
            ByteBuffer buffer = ByteBuffer.wrap(fullData);
            int nameLength = buffer.getInt();

            // 3. Ambil nama file
            byte[] nameBytes = new byte[nameLength];
            buffer.get(nameBytes);
            String originalFileName = new String(nameBytes);

            // 4. Ambil panjang data terenkripsi
            int encryptedLength = buffer.getInt();

            // 5. Ambil data terenkripsi
            byte[] encryptedData = new byte[encryptedLength];
            buffer.get(encryptedData);

            // 6. Dekripsi
            byte[] decryptedData;
            try {
                decryptedData = FileEncryptor.decrypt(encryptedData, password);
            } catch (Exception e) {
                throw new IllegalArgumentException("Password salah atau file rusak!");
            }

            // 7. Simpan file hasil dekripsi
            String decryptedFileName = UUID.randomUUID().toString() + "_decrypted_" + originalFileName;
            File outputFile = Paths.get(uploaddir, decryptedFileName).toFile();
            outputFile.getParentFile().mkdirs();
            Files.write(outputFile.toPath(), decryptedData);

            // 8. Simpan metadata
            DecryptFile extracted = new DecryptFile();
            extracted.setEncryptedFileName(originalFileName);
            extracted.setDecryptedFileName("extracted_from_" + imageFile.getOriginalFilename());
            extracted.setFileName(decryptedFileName);
            extracted.setCreatedAt(LocalDateTime.now());

            repository.save(extracted);
          return decryptedFileName;
    }

}
