package com.baxdigital.steganoapp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SteganographyUtil {

    public static BufferedImage embedLSB(byte[] data, BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage stegoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int dataIndex = 0;
        int bitIndex = 0;

        // Total bit yang akan disisipkan
        int totalBits = data.length * 8;
        int bitCounter = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int[] colors = { red, green, blue };

                for (int i = 0; i < 3; i++) {
                    if (bitCounter < totalBits) {
                        int bit = (data[dataIndex] >> (7 - bitIndex)) & 1;
                        colors[i] = (colors[i] & 0xFE) | bit;

                        bitIndex++;
                        if (bitIndex == 8) {
                            bitIndex = 0;
                            dataIndex++;
                        }
                        bitCounter++;
                    }
                }

                int newRgb = (colors[0] << 16) | (colors[1] << 8) | colors[2];
                stegoImage.setRGB(x, y, newRgb);
            }
        }

        return stegoImage;
    }

    // public static byte[] extractLSB(BufferedImage image) {
    //     ByteArrayOutputStream output = new ByteArrayOutputStream();

    //     int bitCount = 0;
    //     int currentByte = 0;
    //     int totalBytesToRead = -1;
    //     int byteRead = 0;

    //     outer:
    //     for (int y = 0; y < image.getHeight(); y++) {
    //         for (int x = 0; x < image.getWidth(); x++) {
    //             int rgb = image.getRGB(x, y);
    //             int[] colors = {
    //                     (rgb >> 16) & 0xFF,
    //                     (rgb >> 8) & 0xFF,
    //                     rgb & 0xFF
    //             };

    //             for (int color : colors) {
    //                 int lsb = color & 1;
    //                 currentByte = (currentByte << 1) | lsb;
    //                 bitCount++;

    //                 if (bitCount == 8) {
    //                     output.write(currentByte);
    //                     bitCount = 0;
    //                     byteRead++;

    //                     if (totalBytesToRead == -1 && byteRead == 4) {
    //                         byte[] lengthBytes = output.toByteArray();
    //                         totalBytesToRead = ByteBuffer.wrap(lengthBytes).getInt();
    //                         output.reset();
    //                         byteRead = 0;
    //                     }

    //                     if (totalBytesToRead != -1 && byteRead >= totalBytesToRead) {
    //                         break outer;
    //                     }

    //                     currentByte = 0;
    //                 }
    //             }
    //         }
    //     }
   
    //     return output.toByteArray();
    // }
    public static byte[] extractLSB(BufferedImage image) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    int bitCount = 0;
    int currentByte = 0;

    int totalBytesToRead = -1;
    int byteRead = 0;

    // Sementara tampung 8 KB saja (misalnya)
    int maxBytesLimit = 1024 * 1024 * 10; // 10MB

    outer:
    for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
            int rgb = image.getRGB(x, y);
            int[] colors = {
                    (rgb >> 16) & 0xFF,
                    (rgb >> 8) & 0xFF,
                    rgb & 0xFF
            };

            for (int color : colors) {
                int lsb = color & 1;
                currentByte = (currentByte << 1) | lsb;
                bitCount++;

                if (bitCount == 8) {
                    output.write(currentByte);
                    bitCount = 0;
                    byteRead++;

                    if (output.size() > maxBytesLimit) {
                        throw new RuntimeException("Data terlalu besar, ekstraksi dibatasi untuk mencegah kehabisan memori.");
                    }

                    // Ambil panjang nama file
                    if (totalBytesToRead == -1 && byteRead == 4) {
                        byte[] temp = output.toByteArray();
                        int nameLength = ByteBuffer.wrap(temp).getInt();

                        // Total: 4 (int) + nama + 4 (int) + sisa file
                        totalBytesToRead = 4 + nameLength + 4 + 100_000_000; // sementara 100MB max
                    }

                    if (totalBytesToRead != -1 && byteRead >= totalBytesToRead) {
                        break outer;
                    }

                    currentByte = 0;
                }
            }
        }
    }

    return output.toByteArray();
}

}
