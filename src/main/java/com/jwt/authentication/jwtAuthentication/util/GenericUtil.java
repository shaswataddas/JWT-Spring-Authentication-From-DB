package com.jwt.authentication.jwtAuthentication.util;

import java.security.SecureRandom;

public class GenericUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generateResetToken(){
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(40);

        for (int i = 0; i < 40; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(randomIndex));
        }

        return token.toString();
    }
}
