package com.fenoreste.alianza_stp;

import org.springframework.security.crypto.codec.Base64;

public class Main {

	public static void main(String[] args) {
        String username = "leonfranco-cmascore-dev";
        String password = "Q1QNviHK3";
        
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        byte[] encodedCredentialsBytes = Base64.encode(credentialsBytes);
        String encodedCredentials = new String(encodedCredentialsBytes);
        
        System.out.println("Credenciales codificadas: " + encodedCredentials);
	}
}
