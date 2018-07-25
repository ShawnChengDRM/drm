package com.naiye.drm.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {
    public static void main(String[] args) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String modulus = "00833c4af965ff7a8409f8b5d5a83d87f2f19d7c1eb40dc59a98d2346cbb145046b2c6facc25b5cc363443f0f7ebd9524b7c1e1917bf7d849212339f6c1d3711b115ecb20f0c89fc2182a985ea28cbb4adf6a321ff7e715ba9b8d7261d1c140485df3b705247a70c28c9068caabbedbf9510dada6d13d99e57642b853a73406817";
        String exponent = "010001";
        String pubkey = encode64(getPublicKey(modulus, exponent).getEncoded());
        System.out.println("pubkey = " + pubkey);

        String data = "8061eb40cb3cea742205a5107ea5ac7d";
        System.out.println(EncodeRsa(data.getBytes(), pubkey));

        data = "{\"user_agent\":\"Mozilla/5.0 (Linux; U; Android 4.3; en-us; SM-N900T Build/JSS15J) AppleWebKit/534.30 (KHTML, like Ge\",\"language\":\"zh-CN\",\"color_depth\":\"24\",\"pixel_ratio\":\"3\",\"hardware_concurrency\":\"8\",\"resolution\":\"640,360\",\"available_resolution\":\"640,360\",\"timezone_offset\":\"-480\",\"session_storage\":\"1\",\"local_storage\":\"1\",\"indexed_db\":\"1\",\"open_database\":\"1\",\"cpu_class\":\"unknown\",\"navigator_platform\":\"Win32\",\"do_not_track\":\"unknown\",\"regular_plugins\":\"\",\"webgl_vendor\":\"Google Inc.~ANGLE (Intel(R) HD Graphics 630 Direct3D11 vs_5_0 ps_5_0)\",\"adblock\":\"false\",\"has_lied_languages\":\"false\",\"has_lied_resolution\":\"false\",\"has_lied_os\":\"true\",\"has_lied_browser\":\"true\",\"touch_support\":\"1,true,true\",\"js_fonts\":\"Arial,Arial Black,Arial Narrow,Arial Unicode MS,Book Antiqua,Bookman Old Style,Calibri,Cambria,Cambr\"}";
        System.out.println(EncodeRsa(data.getBytes(), pubkey));
    }

    public static String EncodeRsa(byte[] arg9, String postPubKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] v0_2;
        int v7 = 117;
        PublicKey v0 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode64(postPubKey)));
        Cipher v3 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        v3.init(1, ((Key) v0));
        int v4 = arg9.length;
        ByteArrayOutputStream v5 = new ByteArrayOutputStream();
        int v1 = 0;
        int v0_1 = 0;
        while (v4 - v0_1 > 0) {
            v0_2 = v4 - v0_1 > v7 ? v3.doFinal(arg9, v0_1, v7) : v3.doFinal(arg9, v0_1, v4 - v0_1);
            v5.write(v0_2, 0, v0_2.length);
            v0_1 = v1 + 1;
            int v8 = v0_1;
            v0_1 *= 117;
            v1 = v8;
        }
        v0_2 = v5.toByteArray();
        v5.close();
        return byteToString(v0_2);
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

    // //////////////base64
    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
            -1, -1, -1};

    public static String encode64(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2] + "");
                sb.append(base64EncodeChars[(b1 & 0x3) << 4] + "");
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2] + "");
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)] + "");
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2] + "");
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2] + "");
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)] + "");
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)] + "");
            sb.append(base64EncodeChars[b3 & 0x3f] + "");
        }
        return sb.toString();
    }

    public static byte[] decode64(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)) + "");

            do {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)) + "");

            do {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4) + "");
        }
        return sb.toString().getBytes("iso8859-1");
    }

    public static PublicKey getPublicKey(String modulus, String publicExponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus, 16);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent, 16);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return (RSAPublicKey) publicKey;
    }

    public static String getPublicKeyStr(String modulus, String publicExponent) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return encode64(getPublicKey(modulus, publicExponent).getEncoded());
    }
}

