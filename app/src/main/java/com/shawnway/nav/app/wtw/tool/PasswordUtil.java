package com.shawnway.nav.app.wtw.tool;

import android.util.Base64;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.*;


/**
 * AES加密
 */
public class PasswordUtil {
    public static String _key = "G#$%^1234MasWQlZ";

    /**
     * 密码加密
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return new String( Base64.encode(bytes,Base64.DEFAULT),"utf-8").trim();
    }

    /**
     * 密码解密
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = Base64.decode(str.getBytes(), Base64.DEFAULT);
//        byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }

}
