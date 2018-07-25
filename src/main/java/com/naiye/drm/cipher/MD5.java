package com.naiye.drm.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String toMD5(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = HexUtils.BytesToHex(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString.toLowerCase();
    }

    public static String sha1(String str) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(str.getBytes());// 对接后的字符串进行sha1加密
        StringBuilder hexString = new StringBuilder();
        // 字节数组转换为 十六进制 数 的 字符串
        for (int i = 0; i < digest.length; i++) {
            String shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString(); // 签名密文字符串
    }
}
