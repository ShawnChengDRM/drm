package com.naiye.drm.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
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

    // //////////////////md5////
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

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

    private static String MD5_32(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    private static String MD5_32(byte[] strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    private static String MD5_16(String strObj) {
        String resultString = null;
        String aaa = MD5_32(strObj);
        resultString = aaa.substring(8, 8 + 16);
        return resultString;
    }

    // //////////////base64
    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
            -1, -1, -1 };

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

    // ///////////3des
    private final static byte[] IV = { 48, 48, 48, 48, 48, 48, 48, 48 };// { 48,
    // 48,
    // 48,
    // 48,
    // 48,
    // 48,
    // 48,
    // 48
    // };{
    // 0x60,
    // 0x22,
    // 0x11,
    // 0x40,
    // 0x60,
    // 0x22,
    // 0x11,
    // 0x40
    // }
    private static final String Algorithm = "DESede";
    private static final String Algorithm2 = "DESede/CBC/NoPadding";

    private static byte[] cryptBy3Des(String key, int i, byte[] code) {
        try {
            byte[] ccc = MD5_32(key).toLowerCase().substring(0, 24).getBytes("GBK");
            // byte[] ccc = { 100, 51, 101, 55, 98, 102, 102, 52, 56, 54, 102,
            // 98, 55, 56, 100, 98, 51, 55, 49, 100, 57, 49, 54, 54 };

            // byte[] ccc = {97, 50, 56, 55, 49, 57, 97, 99, 101, 48, 57, 53,
            // 97, 53, 97, 97, 98, 51, 101, 48, 98, 101, 102, 55};

            // byte[] ccc = {100, 51, 101, 55, 98, 102, 102, 52, 56, 54, 102,
            // 98, 55, 56, 100, 98, 51, 55, 49, 100, 57, 49, 54, 54};

//			 System.out.println("key "+byteToString(ccc));
            // ccc =
            // parseHexStr2Byte("376365623463666664656531326664353666323638353462");
            SecretKey deskey = new SecretKeySpec(ccc, Algorithm);
            IvParameterSpec localIvParameterSpec = null;
            localIvParameterSpec = new IvParameterSpec(IV);
            Cipher c1 = Cipher.getInstance(Algorithm2);
            c1.init(i, deskey, localIvParameterSpec);
            byte[] asdf = c1.doFinal(code);
            // System.out.println("asdf "+byteToString(asdf));

            return asdf;
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private static byte[] encode3des(String key, byte[] src) {

        return cryptBy3Des(key, 1, src);
    }

    private static byte[] decode3des(String key, byte[] src) {
        return cryptBy3Des(key, 2, src);
    }

    public static String encrypt(String code, String key) {
        int i = code.length() % 8;
        if (i != 0) {
            int j = 8 - i;
            for (int k = 0; k < j; k++)
                code = code + " ";
        }
        byte[] encoded = encode3des(key, code.getBytes());
        return encode64(encoded);
    }

    public static String encrypt(byte[] code, String key) {

        byte[] encoded = encode3des(key, code);
        return encode64(encoded);
    }

    public static String decrypt(String code, String key) throws UnsupportedEncodingException {
        byte[] decoded = decode3des(key, decode64(code));
        return new String(decoded);
    }

    public static String gettime() {
        return new SimpleDateFormat("yyMMddHHmmss").format(new Date(System.currentTimeMillis()));
    }
    public static String gettime2() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
    public static String b(long arg2) {
        String v0 = arg2 < 10 ? arg2 + "" : String.valueOf(((char) (((int) (55 + arg2)))));
        return v0;
    }

    public static String LongToStr(long arg8) {
        long v6 = 36;

        long v0 = arg8 / v6;
        String v4 = "" + b(arg8 % v6);
        while (v0 > 0) {
            v4 = v4 + b(v0 % v6);
            v0 /= v6;
        }

        StringBuffer v0_1 = new StringBuffer(v4);
        v0_1.reverse().toString();

        return v0_1.toString();
    }

    public static String geturldata(String imei, String appversion, String appid, String sdktotalversion, String cpid,
                                    String mac, String ordertime, String appdeveloper, String serviceid, String channelid, String feename,
                                    String sdkversion, String paytype, String appname, String imsi, String uuid, String orderid,
                                    String payfee) {
        String data = "%7B%22imei%22%3A%22";
        data += imei;
        data += "%22%2C%22appversion%22%3A%22";
        data += appversion;
        data += "%22%2C%22appid%22%3A%22";
        data += appid;
        data += "%22%2C%22sdktotalversion%22%3A%22";
        data += sdktotalversion;
        data += "%22%2C%22cpid%22%3A%22";
        data += cpid;
        data += "%22%2C%22mac%22%3A%22";
        data += URLEncoder.encode(mac);
        data += "%22%2C%22ordertime%22%3A%22";
        data += ordertime;
        data += "%22%2C%22appdeveloper%22%3A%22";
        data += URLEncoder.encode(appdeveloper);
        data += "%22%2C%22serviceid%22%3A%22";
        data += serviceid;
        data += "%22%2C%22channelid%22%3A%22";
        data += channelid;
        data += "%22%2C%22feename%22%3A%22";
        data += URLEncoder.encode(feename);
        data += "%22%2C%22sdkversion%22%3A%22";
        data += sdkversion;
        data += "%22%2C%22paytype%22%3A%22";
        data += paytype;
        data += "%22%2C%22appname%22%3A%22";
        data += URLEncoder.encode(appname);
        data += "%22%2C%22imsi%22%3A%22";
        data += imsi;
        data += "%22%2C%22uuid%22%3A%22";
        data += uuid;
        data += "%22%2C%22orderid%22%3A%22";
        data += orderid;
        data += "%22%2C%22payfee%22%3A%22";
        data += payfee;
        data += "%22%7D";
        return encrypt(data, "FILTERMAN");// post key
    }

    public static String getsmsdata(String cpid, String serviceid, String imsi, String orderid, String sdkversion,
                                    String mac) {
        String asc2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// length=
        // 62
        String date1 = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        date1 = "20151230150754";
        String sms = cpid + serviceid + sdkversion + imsi + date1;// 200是sdk版本
        System.out.println("sms明文 " + sms);
        BigInteger bi1 = new BigInteger("62");
        BigInteger bi2 = new BigInteger(sms);
        StringBuilder sb = new StringBuilder();
        while (bi2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger v10 = bi2.remainder(bi1);// 余的结果为
            bi2 = bi2.divide(bi1);// 大数除法
            int v13 = v10.intValue();
            sb.append(asc2.charAt(v13) + "");
        }
        sms = sb.reverse().toString();
        // System.out.println("sms密文 " + sms);//tuxr1oe4NqNary5O8YbqdkyNf5Kfr
        sms = "02" + mac + orderid + sms;
        System.out.println(sms);
        sms = encrypt(sms, "ZTEos10");// "ZTEos10"老版本;//ZTEHYos10新版本密钥
        return sms;
    }

    public static String getsession_id() { // 电信日志里用到的
        String v0 = null;
        try {
            String arg6 = "EgameApp" + System.currentTimeMillis();
            MessageDigest v1_1 = MessageDigest.getInstance("MD5");
            v1_1.update(arg6.getBytes());
            byte[] v3 = v1_1.digest();
            StringBuffer v4 = new StringBuffer("");
            int v2;
            for (v2 = 0; v2 < v3.length; ++v2) {
                int v1_2 = v3[v2];
                if (v1_2 < 0) {
                    v1_2 += 256;
                }

                if (v1_2 < 16) {
                    v4.append("0");
                }

                v4.append(Integer.toHexString(v1_2));
            }

            return v4.toString().toLowerCase();
        } catch (NoSuchAlgorithmException v1) {
            v1.printStackTrace();
            return v0;
        }
    }


    static String convert(int a, int b, String aa) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < aa.length(); i++) {
            sum = sum.multiply(BigInteger.valueOf(a)).add(BigInteger.valueOf(getnum(aa.charAt(i))));
        }
        String bb = "";
        while (!sum.equals(BigInteger.ZERO)) {
            bb = retchar(sum.mod(BigInteger.valueOf(b)).intValue()) + bb;
            sum = sum.divide(BigInteger.valueOf(b));
        }
        if (bb.equals(""))
            bb = "0";
        return bb;
    }

    public static String aaa(int arg6) {
        double v2 = Math.random();
        int v0;
        for (v0 = 0; v0 < arg6; ++v0) {
            v2 *= 10;
        }

        String v0_1 = "" + new DecimalFormat("0.00").format(v2);
        v0_1 = v0_1.substring(0, v0_1.length() - 4);
        System.out.println(v0_1);
        return convert(10, 62, v0_1);
    }

    public static String getRSAKey(String data) throws Exception {
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxdY09b2nOXpELVC+4Y/8k79Noqdgvb0BhFQmZbSnFScaJdm1rWfLZy43SQ2BSJSyUwWMaq1uu0pAzfOHVR688Y/zpPmD3yVoBIrTL6EIKCA77fmWYJVWzGQ+8hlIRnGT454Ep4MquKYJCv2x+ReBG3mr27+mE4rUvHrmrhsyRGwIDAQAB";
        PublicKey v0 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode64(key)));
        Cipher v1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        v1.init(1, ((Key) v0));
        return encode64(v1.doFinal(data.getBytes()));
    }
    static int getnum(char m) {
        if (m >= '0' && m <= '9')
            return m - '0';
        else if (m >= 'A' && m <= 'Z')
            return m - 'A' + 10;
        else
            return m - 'a' + 36;
    }

    static char retchar(int i) {
        if (i <= 9)
            return (char) (i + '0');
        else if (i >= 10 && i <= 35)
            return (char) (i - 10 + 'A');
        else
            return (char) (i - 36 + 'a');
    }

    static String jinzhizhuanhuan(int a, int b, String aa) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < aa.length(); i++) {
            sum = sum.multiply(BigInteger.valueOf(a)).add(BigInteger.valueOf(getnum(aa.charAt(i))));
        }
        String bb = "";
        while (!sum.equals(BigInteger.ZERO)) {
            bb = retchar(sum.mod(BigInteger.valueOf(b)).intValue()) + bb;
            sum = sum.divide(BigInteger.valueOf(b));
        }
        if (bb.equals(""))
            bb = "0";
        System.out.println(bb);
        return bb;
    }
    public static String gettime14() {
        String result = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        result = dateFormat.format(now);
        return result;
    }
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }
    private static byte[] getBytes (char[] chars) {
        Charset cs = Charset.forName ("UTF-8");
        CharBuffer cb = CharBuffer.allocate (chars.length);
        cb.put (chars);
        cb.flip ();
        ByteBuffer bb = cs.encode (cb);

        return bb.array();

    }
    public static void main(String[] args) throws Exception {
        String print = "";
        // String key = aaa(100);
        // System.out.println(key);
        // String rsakey = getRSAKeyForMonth(key);
        //
        // System.out.println(rsakey);
        //
        //
        //
        // System.out.println(HttpRequest.sendPost("http://106.14.21.136:13888/BYPay/ddo-pay/ddoInit.do?mobile=13635256041&pid=1001",
        // ""));

        print = "0jUazexutPV7L/AEJBFfUiqHFhkN5pvigp51HmfD+Z0gbgrSDwdxzhIoXVD2ARVSxpYsVhCjA0keGuuuShkIFv241j3T2Bqc5gEP+zwPRDbmwcACdKQhDgNehX3jA/eNalkYjqBYJpAk8OS7ZcjZ0V2UAyQ/iVHBYRByidkW1Z+Yb+6G8a6KZNP/i4BCCo1CUR0o7DLZCcRmWRKh5A4hyZ/qNIFatdrS1DR/1I5cz20n8cdUgNesCRplT8U5d2r7sCYsoASQyVqcH3fztMyTgCUA3duiXEW+cJNo718rUfHRSS+2rG5KQ3Kmddsuy351uJplVRw0s2BYG2DaaxZmtk6VvXQEkFBZbuwWYk2uYezSxmZZ9/2ylaO6vV3BcrdQ4aOuzYn2xT9k1+5pOB+uErB8fZOIMqyvhRk1DIF5goUd33bnv7CYq+TV1XfvfegNYuUCKM+ahaZtRxpq+v5Pjo2Z/vCC28eYurpdyVH4il0uRM5PbURfUoTwp7tt4osJ6nioNVpkTq9hKc7FyTv52yv9eBV+5JHjRmPmz9MrwuUzZIsawYleOS6HAe6XLqfkSQt0rBaCDG9onm0EfKytev7mekGeeNy2jkp5Upq38LnlonW2x7T5li+dgAxgXL1oWUCdvEjiEEbSgpV251rmvCloeWXD+GHuYJGJaLI/UsLeDh+LU/WJ4x8/jMFRP5cfDhiHnRAz45g6iMeC/6eDmehEXK2BTTyuHCvnq0f80RDU6TqA5vJK+scsVUOi3WyI/nroFCu7cSThEWcjfxnX1DCzab3uUrogdAqo5qKPUp/j4JsFJrkKgbaMOcCo136a1DGeR833TXtC+yCwN+jngVHKm7GYV2mBauRoE8PVsrNpR4UAPlR2ZIVdyjUzZmiHbX+oiFpSZWTQrH1J1moWx13z2IbNcCLP83ZThfWxOC9CDmag+U+QoytAGofh5BjJK0XnkC0roA38M1YqOA61T0U+9RUjM84nKff3wGQhO7WHPkFNXWjMjVXcmwrOpPCBkFgID2/qSTIFCNG91uN0rQcZaG4x0ZBNxu5uqBJEKyxohwYlis79RMhXUNZKEa0vK6op61p3rcDgNScisXGtmSHkYx4XCO/ulKwJ69N6j2WFuVvjJZBXeBFXtlCzp8EvEgFt84DOouT/Mlyhw1VxnnKqatVLT8eVew84MJLAvLvvZ40+vSsjPA==";



        print = decrypt(print, "FILTERMAN");//5mJwu^9!sBZymx*v
// 2s9ualtkd7fb8p4 FILTERMAN    unipayLogKey
        //WS16S#SWLS082Sn8sNH761B1a988 短信key
        //SUH61HN8de68djeHHS4Na0o  Fd85Eb48eb8DCd6Y3fd0dA0c2R

        System.out.println(print);

        System.out.println(java.net.URLDecoder.decode(print, "UTF-8"));


//		String PayCode_36 = jinzhizhuanhuan(62, 10, "fJK2AAWq8w2S2DzSLWC8mi");
//
//		 System.out.println("md5 = "+MD5_32("13221055086".substring(5)+"sub"));
//		 System.out.println("md5 = "+MD5_32("13944462089"));

        print = "7324A37A0827FFA2E78B3EFF65468B9A86AFBCD0486D8B9DAE5EC4231AFF1E443B40854A8D59DE885B4A0CA45ABE1CD748D81828CF160F79C2412AACD98133CAB01D21915DD4476D7ABA821E5E9BBD2A063B9D410A9349AF1A0E19580B88984627E8AC0793F724F585E364DEBC90323F03C446B6BB6DC9AA85FBF2757B5C2C7BADEF5AC01A9500159885DE6F6C1A6E1A38B503B7ED862D93DA57DB85A1BCAC27369C537D709867EFF476B218A1E088DD18DD61E0ED1FAE643C352418A93F5D747668A422CE7C78211C8AD22C6F43D18C89979442166E4EDBF2511881C5808AA9ABFD6295DC90402FBC5D4D85DA2D6D7EFDF640FA8AFE603879FE1664566D9DC65A85C2DD20FBE8F1223398F260BE8D45CDB45AD5A8615DF1802EA9AFB8BB077FB313F671BEC5A8BF24";




        //		print ="MUM1MDQwQkYyQUUwRTRDNkM2@http://passport.migu.cn/@f427c27116e44beb87d60ed3520456e3";
        System.out.println(encode64(parseHexStr2Byte(print)));
//		System.out.println((new String (parseHexStr2Byte(print))));
//		System.out.println(byteToString(print.getBytes()));
//		System.out.println(new String(decode64(print)));
//		System.out.println("key = "+byteToString(decode64(print)));
//		System.out.println(new String(parseHexStr2Byte(print)));


//		byte[] ccc = {48, 57, 56, 101, 100, 55, 98, 49, 102, 50, 97, 54, 99, 52, 48, 99, 56, 98, 50, 55, 55, 101, 56, 52, 99, 100, 100, 50, 98, 52, 55, 101, 52, 91, -27, -110, -86, -27, -110, -107, -28, -72, -128, -23, -108, -82, -25, -103, -69, -27, -67, -107, 93};


//		byte[] ccc = {48, 49, 52, 51, 99, 48, 98, 55, 54, 54, 100, 102, 55, 99, 53, 57, 99, 54, 55, 101, 99, 101, 54, 48, 100, 53, 101, 51, 100, 56, 98, 56, 101, 91, -27, -110, -86, -27, -110, -107, -28, -72, -128, -23, -108, -82, -25, -103, -69, -27, -67, -107, 93};
//
//
//
//		System.out.println(new String(ccc));


//		System.out.println(System.getProperty("java.version")); //判断JDK版本
//		System.out.println(System.getProperty("sun.arch.data.model")); //判断是32位还是64位

    }

}

