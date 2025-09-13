package com.baxdigital.steganoapp.util;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FileResponse {
    private int count;
    private int countDecrypt;
    private List<DTOFiles> results;
}