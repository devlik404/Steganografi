package com.baxdigital.steganoapp.service;

import com.baxdigital.steganoapp.model.EncryptedFile;
import com.baxdigital.steganoapp.repository.EncryptedFileRepository;
import com.baxdigital.steganoapp.util.FileEncryptor;
import com.baxdigital.steganoapp.util.SteganographyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class EncryptedFileService {

    @Autowired
    private EncryptedFileRepository repository;
    @Autowired
    private Cloudinary cloudinary;

    private final String uploaddir = "./uploads/";

    public void saveMetaData(MultipartFile file, MultipartFile image, String password) throws Exception {

        // 1. Enkripsi file
        byte[] encryptedData = FileEncryptor.encrypt(file.getBytes(), password);

        // 2. Tambahkan panjang data di depan (4 byte)
        String originalName = file.getOriginalFilename(); // misal: tbl_orders.xlsx
       
        byte[] nameBytes = originalName.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(nameBytes.length); // 4 byte panjang nama
            dos.write(nameBytes); // nama file
            dos.writeInt(encryptedData.length); // 4 byte panjang data
            dos.write(encryptedData); // isi terenkripsi
        }

        byte[] dataToEmbed = baos.toByteArray();

        // 3. Persiapkan gambar (konversi ke TYPE_INT_RGB)
        BufferedImage inputImage = ImageIO.read(image.getInputStream());
        BufferedImage rgbImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgbImage.createGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.dispose();

        // 4. Validasi kapasitas
        int width = rgbImage.getWidth();
        int height = rgbImage.getHeight();
        int maxCapacity = width * height * 3 / 8;

        if (dataToEmbed.length > maxCapacity) {
            throw new IllegalArgumentException("Ukuran file terenkripsi melebihi kapasitas gambar!");
        }

        // 5. Sisipkan data
        BufferedImage stegoImage = SteganographyUtil.embedLSB(dataToEmbed, rgbImage);

        // 6. Simpan gambar hasil stego
        String stegoFileName = UUID.randomUUID() + "_stego.png";
        File outputImage = Paths.get(uploaddir, stegoFileName).toFile();
        outputImage.getParentFile().mkdirs();
        ImageIO.write(stegoImage, "png", outputImage);
        System.out.println("Disimpan ke: " + outputImage.getAbsolutePath());
        // upload to cloudinary
      Map uploadParams = ObjectUtils.asMap(
    "folder", "stegano_images"  // contoh folder
);
        Map uploadResult = cloudinary.uploader().upload(outputImage, uploadParams);
        String imageUrl = uploadResult.get("secure_url").toString();

        System.out.println("URL hasil upload: " + imageUrl);
        // 7. Simpan metadata ke database
        EncryptedFile encryptedFile = new EncryptedFile();
        encryptedFile.setOriginalFileName(file.getOriginalFilename());
        encryptedFile.setEncryptedFileName(file.getOriginalFilename() + ".enc");
        encryptedFile.setStegoImageFileName(imageUrl);
        encryptedFile.setPassword(password);
        encryptedFile.setFileSize(file.getSize());
        encryptedFile.setFileType(file.getContentType());
        encryptedFile.setUploadDate(LocalDateTime.now());

        repository.save(encryptedFile);
    }
}
