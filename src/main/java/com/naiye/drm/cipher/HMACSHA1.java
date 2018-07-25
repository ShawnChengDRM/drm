package com.naiye.drm.cipher;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA1 {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /*
     * 展示了一个生成指定算法密钥的过程 初始化HMAC密钥
     * @return
     * @throws Exception
     *
      public static String initMacKey() throws Exception {
      //得到一个 指定算法密钥的密钥生成器
      KeyGenerator KeyGenerator keyGenerator =KeyGenerator.getInstance(MAC_NAME);
      //生成一个密钥
      SecretKey secretKey =keyGenerator.generateKey();
      return null;
      }
     */

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception
    {
        byte[] data=encryptKey.getBytes(ENCODING);
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        //完成 Mac 操作
        return mac.doFinal(text);
    }

    public static String combine(int method, String browserType, String osType, String ClientTime, String field_1, String field_2, String field_3, String field_4, String authId, String authSessionId, String cid)
    {
        int index = method % 3;
        if(index == 2)
        {
            return browserType + osType  +   authId  +   field_4 + field_1 + ClientTime +  authSessionId  + field_3 + field_2 + cid;
        }
        else if(index == 1)
        {
            return field_3 + field_2 + cid + browserType + osType + authId + field_4 + field_1 + ClientTime + authSessionId;
        }
        else
        {
            return cid + browserType + osType  +   authId  +   field_3 + field_2 + field_4 + field_1 + authSessionId + ClientTime;
        }
    }

    public static String B2H(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    public static String genKey(String rawKey, String authSessionId, String version, String netId)
    {
        String message = rawKey + authSessionId;
        String key = version + netId;
        try
        {
            return B2H(HMACSHA1.HmacSHA1Encrypt(message, key)).toLowerCase();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
        String authSessionId = "e8e9066dc3914195823f69dd3b6651db";
        Integer method = 816;
        String browserType = "Netscape";
        String osType = "Win32";
        String time = "20180313152625816";
        String field_1 = "";
        String field_2 = "";
        String field_3 = "";
        String field_4 = "";
        String authId = "8dd64fe4ad5340ad90c558a583518913";
        String cid = "C7E86EE69C600001";
        String raw = HMACSHA1.combine(method, browserType, osType, time, field_1, field_2, field_3, field_4,authId, authSessionId, cid);
        System.out.println(HMACSHA1.B2H(HMACSHA1.HmacSHA1Encrypt(raw, authSessionId+time)).toLowerCase());
    }
}