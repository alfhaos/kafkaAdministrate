package com.kafka.administrate.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {
	
	 public static String encryptPassword(String password) {
		 
	        String encryptedPassword = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashInBytes) {
	                sb.append(String.format("%02x", b));
	            }
	            encryptedPassword = sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            // Handle exception
	        }
	        return encryptedPassword;
	    }
}
