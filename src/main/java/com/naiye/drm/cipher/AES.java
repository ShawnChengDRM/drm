package com.naiye.drm.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AES {

    public static byte[] Encrypt(byte[] key, byte[] initVector, byte[] data) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            Security.addProvider(new BouncyCastleProvider());
            byte[] encrypted = cipher.doFinal(data);
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] Decrypt(byte[] key, byte[] initVector, byte[] encrypted) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);

            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //0,0,1,96,74,228,32,71,1,4,0,0,187,255,1,1,0,0,0,0,0,2,31,1,235,3,249,15,100,104,198,187,118,73,191,43,241,216,50,63,82,41,21,252,16,171,143,45,53,130,21,210,238,117,10,2,173,142,88,22,65,151,90,67,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,43,233,195,22,231,115,92,160,33,203,11,187,150,140,26,36,250,119,196,217,212,55,14,145,218,216,242,10,18,21,189,37,154,161,69,243,151,9,32,250,0,0,0,0,100,0,0,0,0,233,141,169,244,89,122,8,159,57,92,143,114,30,27,152,144,2,238,207,153,229,127,120,96,8,231,162,57,102,2,0,192,168,3,155,222,240,1,13,0,233

    public static void main(String[] args)
    {
        byte[] key = new byte[]{39, 27, 59, 74, 26, (byte) 165, (byte) 213, (byte) 238, (byte) 251, 83, 26, 121, (byte) 166, 63, (byte) 243, 4, 26, (byte) 251, 123, 76, 105, (byte) 217, 63, 111, (byte) 147, (byte) 188, 66, 6, 42, 69, 59, (byte) 222};
        byte[] initVector = new byte[]{(byte) 152,21,30,7,124,(byte) 243,113,(byte) 216,(byte) 185,61,64,(byte) 247,8,(byte) 176,(byte) 142,(byte) 214};
        byte[] encrypted = new byte[]{(byte) 218,(byte) 205,
                (byte) 128,(byte) 173,(byte) 146,106,55,23,(byte) 194,(byte) 169,17,(byte) 160,85,(byte) 227,118,(byte) 228,45,(byte) 144,104,0,(byte) 166,63,41,30,31,83,93,(byte) 238,
                (byte) 171,5,(byte) 159,(byte) 133,93,(byte) 205,104,125,(byte) 217,10,48,(byte) 176,(byte) 164,(byte) 210,81,34,84,(byte) 226,3,1,(byte) 168,(byte) 220,37,(byte) 192,
                48,102,(byte) 239,(byte) 254,31,(byte) 145,(byte) 219,120,111,(byte) 192,(byte) 203,(byte) 166,(byte) 177,82,72,115,(byte) 130,(byte) 209,56,(byte) 212,(byte) 213,
                67,90,(byte) 129,(byte) 246,113,41,(byte) 199,(byte) 220,41,88,127,9,(byte) 237,102,(byte) 154,(byte) 243,51,59,97,82,(byte) 247,
                (byte) 170,(byte) 197,(byte) 206,(byte) 165,108,(byte) 249,11,(byte) 170,(byte) 158,(byte) 181,(byte) 187,(byte) 194,88,
                16,(byte) 150,63,56,22,(byte) 146,(byte) 236,11,94,110,(byte) 145,(byte) 157,(byte) 231,8,57,55,90,42,(byte) 137,7,
                15,8,(byte) 218,(byte) 144,(byte) 139,20,(byte) 130,(byte) 239,102,(byte) 216,40,111,(byte) 128,(byte) 147,44,93,(byte) 224,(byte) 159,(byte) 234,(byte) 229,(byte) 148,
                45,6,(byte) 184,(byte) 195,(byte) 128,23,85,24,48,96,34,48,(byte) 185,84,(byte) 252,(byte) 219,39,127,(byte) 198,49,30,(byte) 132,(byte) 255,
                110,22,92,(byte) 222,108,117,(byte) 189,(byte) 245,47,(byte) 219,(byte) 184,29,(byte) 203,(byte) 161,19,75,93,(byte) 155,111,(byte) 147,(byte) 174};
        byte[] dec = AES.Decrypt(key, initVector, encrypted);
        System.out.println(dec);
    }
}
