package com.baxdigital.steganoapp.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DTOFiles {
    private long id;
private  String fileName;
 private int fileSize;
 private String url;
 private LocalDate uploadDate;
}



