package com.fenoreste.utilidades;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CustomOkHttpClient {

	
	   public OkHttpClient createOkHttpClient() {
	        try {
	            // Crea un SSLContext personalizado que confíe en todos los certificados
	            SSLContext sslContext = SSLContext.getInstance("TLS");
	            sslContext.init(null, new TrustManager[] { trustAllCertificates() }, null);

	            // Configura el cliente OkHttp con el SSLContext personalizado
	            OkHttpClient.Builder builder = new OkHttpClient.Builder();
	            builder.sslSocketFactory(sslContext.getSocketFactory(), trustAllCertificates());

	            return builder.build();
	        } catch (NoSuchAlgorithmException | KeyManagementException e) {
	            // Manejar la excepción
	            return null;
	        }
	    }

	    private X509TrustManager trustAllCertificates() {
	        return new X509TrustManager() {
	            @Override
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	                // No hace nada
	            }

	            @Override
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	                // No hace nada
	            }

	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                return new X509Certificate[0];
	            }
	        };
}
}