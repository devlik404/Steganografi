package com.baxdigital.steganoapp.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class SteganographyUtil {


    public static BufferedImage embedLSB(byte[] data, BufferedImage image) throws IOException {
        int dataIndex = 0;
        int dataLength = data.length * 8;

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);

                int newPixel = replaceLSB(pixel, data[dataIndex / 8], dataIndex % 8);
                image.setRGB(j, i, newPixel);
                dataIndex++;

                if (dataIndex * 8 >= dataLength) {
                    return image;
                }
            }
        }
        return image;

    }

    // Helper untuk mengganti bit LSB dari pixel
    private static int replaceLSB(int pixel, byte dataByte, int bitIndex) {
        int mask = 0xFE; // Mask untuk mengatur bit LSB ke 0
        int bit = (dataByte >> (7 - bitIndex)) & 1; // Ambil bit tertentu dari data
        pixel &= mask; // Set LSB ke 0
        return (pixel | (bit << 16)); // Gabungkan bit baru ke LSB
    }
}

