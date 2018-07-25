package com.naiye.drm.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


public class Tools {
    private Tools() {
    }

    public static String gettime17() {
        String result = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        result = dateFormat.format(now);
        return result;
    }

    public static String gettime16() {
        String result = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        result = dateFormat.format(now);
        result = result.substring(0, 16);
        return result;
    }

    public static String gettime15() {
        String result = "";
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        result = dateFormat.format(now);
        result = result.substring(0, 15);
        return result;
    }

    public static String getUUID() {
        Date dc = new Date();
        Long t = dc.getTime() + 12216643200000L;
        String tl = getIntegerBits(t, 0, 31);
        String tm = getIntegerBits(t, 32, 47);
        String thv = getIntegerBits(t, 48, 59) + '1';
        String csar = getIntegerBits(rand(4095), 0, 7);
        String csl = getIntegerBits(rand(4095), 0, 7);
        String n = getIntegerBits(rand(8191), 0, 7) + getIntegerBits(rand(8191), 8, 15) + getIntegerBits(rand(8191), 0, 7) + getIntegerBits(rand(8191), 8, 15) + getIntegerBits(rand(8191), 0, 15);
        String uuid = tl + tm + thv + csar + csl + n;
        return uuid;
    }

    public static Long rand(int max) {
        return Math.round(Math.random() * (double)(max + 1));
    }

    public static String getIntegerBits(Long t, int start, int end) {
        StringBuilder sb = new StringBuilder();
        String base16 = Long.toHexString(t);

        for(int i = (int)Math.floor((double)(start / 4)); i <= (int)Math.floor((double)(end / 4)); ++i) {
            if (base16.length() <= i) {
                sb.append('0');
            } else {
                sb.append(base16.charAt(i));
            }
        }

        return sb.toString().toUpperCase();
    }

    public static String dateFormat1(String t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(t);
        String time = sdf.format(date);
        return time;
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
}


