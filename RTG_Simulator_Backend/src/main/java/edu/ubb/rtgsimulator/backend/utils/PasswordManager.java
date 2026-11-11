package edu.ubb.rtgsimulator.backend.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {
    public static String generateHashPassword(String password, String salt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] input = (password + salt).getBytes();
            messageDigest.reset();
            messageDigest.update(input);

            byte[] output = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b:output){
                sb.append(String.format( "%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

