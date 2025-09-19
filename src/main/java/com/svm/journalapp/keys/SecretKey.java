package com.svm.journalapp.keys;

import io.jsonwebtoken.security.Keys;

public class SecretKey {
    private static String generateSecretKey(){
        javax.crypto.SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static void main(String[] args){
        System.out.println(generateSecretKey());
    }
}
