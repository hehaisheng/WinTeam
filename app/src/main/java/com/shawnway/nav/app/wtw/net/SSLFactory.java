package com.shawnway.nav.app.wtw.net;

import android.content.Context;

import com.shawnway.nav.app.wtw.R;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Cicinnus on 2016/11/24.
 * HTPPS证书
 */

public class SSLFactory {

    private static SSLSocketFactory sslSocketFactory;

    public static SSLSocketFactory getSSLSocketFactory(Context context) {

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream caInput = context.getResources().openRawResource(R.raw.ca);
            Certificate ca = null;
            try {
                ca = certificateFactory.generateCertificate(caInput);
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                caInput.close();
            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            if (ca == null) {
                return null;
            }
            keyStore.setCertificateEntry("ca", ca);
            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
