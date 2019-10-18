package org.studyintonation.course_inspector.info_generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class Hasher {
    static String hash(String pathToFile) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(pathToFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] dataBytes = new byte[1024];

        int read;
        try {
            while ((read = fileInputStream.read(dataBytes)) != -1) {
                messageDigest.update(dataBytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] mdBytes = messageDigest.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte mdByte : mdBytes) {
            hexString.append(String.format("%02x", mdByte));
        }
        return hexString.toString();
    }
}
