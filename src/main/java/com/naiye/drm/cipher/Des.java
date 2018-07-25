package com.naiye.drm.cipher;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Des {

    public static void main(String args[]) throws Exception {

        // 下面1个 是
        // http://114.55.176.181:2222/ygapi/getYGConfigByNetId.wckj?netId=3189166271cb45e392a5e201bddb1480&reload=1

        //http://116.62.11.1:5001/game2/index.html

        String rawKey = "5a4459784d6a686b4d44426a4e474a69";
        String authSessionId = "e95b4b9026e649398e2ff0f6e8711487";
        String version = "1528215257466";
        String netId = "e8036b8a86e84c939f1ee8fe64284129";
        String s = "edb06e470ec4a02549544eb16a6164e81826ebbce150269095bf020dcbec2297d132181c6bb0f494a2f1dc4b06a6164f0f5e14871b33ce9a28904f9c6e599f5fd752ba5c51118d250161f5a7b3c7c8519a411e4a0e8ab73b88245e4c9bdd9a97a9c59414145b8325ec379777873416874dfd44d778bf28846fc39c111147bdd0fadc3d0f1a009180c9c137a37b14dc0d63f153e6347e3d8fae7127d80f2212609276af458cbb9001c845f6667d6ff7db5ea01322a9b37f587218ad5a83b2f57a72d9df4c56ba52d1c4c018874dd5d7209c062de209b3e91cc7a9cca19079b941ab474a7dbef92097e30e267d970acd4752cf3af765948425f7df72793c979a4a69bb8991bfd15b1ece7ff4d8ed02981f337c82504e5aed4c714fad3efd750a91f41b8f2502a5b142ced7fc18d9df74827edab7465efdf08b5ca952146503d3a3035ddd25d945c25a0c1f29579534a807c7dfd02a3e92234843dc53792b4ba9b368fbb87763b19de32d4643c8c7f5e68859a520a54f4dc4d388bbb62d773d46e4f1dd850b444abcd0be18dd5adf325272";
//        System.out.println(		new String(Base64.getDecoder().decode(Des.decrypt(s,HMACSHA1.genKey(rawKey, authSessionId, version, netId)))));

        String key = HMACSHA1.genKey(rawKey, authSessionId, version, netId);
        System.out.println("key " + key);
        System.out.println(Des.decrypt(s,key));
    }


    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static String encrypt(String datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return byteToString(cipher.doFinal(datasource.getBytes()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static String decrypt(String src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数�?
        SecureRandom random = new SecureRandom();
        // 创建�?��DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // DESKeySpec desKey = new DESKeySpec(parseHexStr2Byte(password));

        // 创建�?��密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        return new String((cipher.doFinal(parseHexStr2Byte(src))), "UTF-8");
    }


}