package com.naiye.drm.util;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class DisableSSLCertificateCheckUtil {
    private static final Logger logger = LoggerFactory.getLogger(DisableSSLCertificateCheckUtil.class);

    /**
     * Prevent instantiation of utility class.
     */

    private DisableSSLCertificateCheckUtil() {

    }

    /**
     * Disable trust checks for SSL connections.
     * 调用一次，影响整个TOMCAT对证书的验证
     */
    public static void disableChecks() {
        try {
            new URL("https://0.0.0.0/").getContent();
        } catch (IOException e) {
            // This invocation will always fail, but it will register the
            // default SSL provider to the URL class.
        }
        try {
            SSLContext sslc = SSLContext.getInstance("TLS");
            TrustManager[] trustManagerArray = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            sslc.init(null, trustManagerArray, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String s, SSLSession sslSession) {
//                    return true;
//                }
//            });
        } catch (Exception e) {
            logger.error("error msg:{}", e);
            throw new IllegalArgumentException("证书校验异常！");
        }
    }
}
