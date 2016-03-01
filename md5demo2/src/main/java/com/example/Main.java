package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        String password = "12345";
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                // System.out.println("hah:"+hexString);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            System.out.println("md5:"+sb.toString());
            /**
             * md5都是32位
             */
            System.out.println("md5 length:"+sb.toString().length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }
}
