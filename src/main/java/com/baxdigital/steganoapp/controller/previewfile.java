package com.baxdigital.steganoapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/")
public class previewfile {
    @GetMapping("preview/{filename}")
    public void previewFile(@PathVariable String filename, HttpServletResponse response) {
        try {
            File file = Paths.get("./extract", filename).toFile();
            if (!file.exists()) {
                response.setStatus(404);
                response.getWriter().write("File tidak ditemukan");
                return;
            }
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

            try (InputStream in = new FileInputStream(file);
                 OutputStream out = response.getOutputStream()) {
                byte[] buff = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buff)) != -1) {
                    out.write(buff, 0, bytesRead);
                }
                out.flush();
            }
            if (!file.delete()) {
                System.err.println("Gagal menghapus file: " + filename);
            }

        } catch (Exception e) {
            response.setStatus(500);
        }
    }

}
