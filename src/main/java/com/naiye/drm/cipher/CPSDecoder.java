package com.naiye.drm.cipher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class CPSDecoder
{
    private String key1 = "ef ghi  jklmnoL U3F9\\_XM?Ep  q rs1PW\');A0@.7I<JDC=:RV85-O6]t uv[ QG#`^BY,/K$%&S(2!\"4+TH>*ZNacbd";

    private byte[] bkey1 = new byte[127];

    //"q", "r", "c", "k", "l", "m", "D", "o", "E", "x", "t", "h", "W", "J", "i", "H", "A", "p", "1", "s", "V", "Y", "K", "U", "3", "R", "F", "M", "Q", "w", "8", "I", "G", "f", "P", "O", "9", "2", "b", "v", "L", "N", "j", ".", "7", "z", "X", "B", "a", "S", "n", "u", "0", "T", "C", "6", "g", "y", "_", "4", "Z", "e", "5", "d", "{", "}", "|", "~", " ", "!", "#", "$", "%", "(", ")", "*", "+", ",", "-", ":", "=", "?", "@", "[", "]", "^"
    private String key2 = "qrcklmDoExthWJiHAp1sVYKU3RFMQw8IGfPO92bvLNj.7zXBaSnu0TC6gy_4Ze5d{}|~ !#$%()*+,-:=?@[]^";

    private String[] cst4a = new String[] { "cv7ffqcxE", "",
            "36Ka8841WOUfnIluipAq", "6nygXnQEX" };

    private int ft = 1;

    private String[] cst;

    private int ou;

    public void getbKey1()
    {
        for(int i = 0; i < 32; i++)
        {
            bkey1[i] = 0;
        }

        for(int j = 0; j < key1.length(); j++)
        {
            bkey1[j + 32] = (byte)(key1.charAt(j) - 33);
        }
    }

    public int[] xC(String test)
    {
        int[] el = decode(test);
        int fr = (el[0] << 8) + el[1];
        for (int i = 2; i < el.length; i += 2)
        {
            el[i] ^= ((fr >> 8) & 0xFF);
            if (i + 1 < el.length)
            {
                el[i + 1] ^= (fr & 0xFF);
            }
            fr++;
        }

        return slice(el, 2, el.length);
    }

    public static String tA(int[] le)
    {
        int cp = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < le.length;)
        {
            int gp = le[i];
            if (gp < 0x80)
            {
                cp = gp;
            }
            else if (gp < 0xc0)
            {
                cp = '?';
            }
            else if (gp < 0xe0)
            {
                cp = ((gp & 0x3F) << 6) | (le[i + 1] & 0x3F);
                i++;
            }
            else if (gp < 0xf0)
            {
                cp = ((gp & 0x0F) << 12) | ((le[i + 1] & 0x3F) << 6)
                        | (le[i + 2] & 0x3F);
                i += 2;
            }
            else if (gp < 0xf8)
            {
                cp = '?';
                i += 3;
            }
            else if (gp < 0xfc)
            {
                cp = '?';
                i += 4;
            }
            else if (gp < 0xfe)
            {
                cp = '?';
                i += 5;
            }
            else
            {
                cp = '?';
            }
            bos.write((byte)(cp));
            i++;
        }

        return new String(bos.toByteArray());
    }

    public static void decodeConst4a(byte[] bc, int start, int end, int deep)
    {
        int bstart = (int)Math.floor((start + end) / 2);
        if(deep > 0)
        {
            deep--;
            if(bstart - start >= 3)
            {
                decodeConst4a(bc, start, bstart, deep);
            }

            if(end - bstart >= 3)
            {
                decodeConst4a(bc, bstart, end, deep);
            }
        }

        for(int i = start; i < bstart; i += 2)
        {
            byte b = bc[i];
            int ls = end - 1 - (i - start);
            bc[i] = bc[ls];
            bc[ls] = b;
        }
    }

    public byte[] getBrowserSHA1()
    {
        //zh-CN,zh:495:887:24:24:-480:0:1:0:1:0:1:1:1:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:1:ltXHhsKcmwhXwALc.3y3m9DUK9W:TkQi9w.3ZGL1mM02bjmmZyQxEHL:t5vkmXwuUtqfnaX42LaFxHN6SJ9
        //zh-CN,zh:349:887:24:24:-480:0:1:0:1:0:1:1:1:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:ltXHhsKcmwhXwALc.3y3m9DUK9W:TkQi9w.3ZGL1mM02bjmmZyQxEHL:t5vkmXwuUtqfnaX42LaFxHN6SJ9
        //zh-CN,zh:349:887:24:24:-480:0:1:0:1:0:1:1:1:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:ltXHhsKcmwhXwALc.3y3m9DUK9W:TkQi9w.3ZGL1mM02bjmmZyQxEHL:t5vkmXwuUtqfnaX42LaFxHN6SJ9
        String browserInfo = "zh-CN,zh:349:887:24:24:-480:0:1:0:1:0:1:1:1:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:ltXHhsKcmwhXwALc.3y3m9DUK9W:TkQi9w.3ZGL1mM02bjmmZyQxEHL:t5vkmXwuUtqfnaX42LaFxHN6SJ9";
        // Screen {availWidth: 412, availHeight: 732, width: 412, height: 732,
        // colorDepth: 24…}
        // new Date().getTimezoneOffset()
        // devicePixelRatio
        // 25个检查对象是否存在"safari,ontouchstart,sidebar,localStorage,clipboardData,sessionStorage,indexedDB,openDatabase,standalone,$PreUCBrowserClassic,UCBrowserMessageCenter,__firefox__,_firefox_ReaderMode,__mttCreateFrame,mttCustomJS,__crWeb,__gCrWeb,MicroMessenger,SogouMse,ucweb,qb_bridge,FaveIconJavaInterface,jesion,dophin,orientation"
        // $_f0 = canvs信息toDataUrl
        // $_f1 = canvs
        // if $_fh0存在也加上
        // 分号串联
        byte[] ba = sha1(browserInfo);
        return ba;
    }

    public byte[] pushInfo(int r0)
    {
        int r1 = 16777216;//109520064 1090519040
        ByteArrayOutputStream bos = new ByteArrayOutputStream();


        int q2 = 0;
        int bq = 2;
        byte[] ba = int2byte(new int[]{q2, r1, bq});
        try
        {
            bos.write(1);
            bos.write(r0);
            bos.write(ba);
            bos.write(31);//_$sj.push(_$wi, _$sg);
            bos.write(1);
            bos.write(getBrowserSHA1());// 浏览器信息
            bos.write(ia2ba(decode("ltXHhsKcmwhXwALc.3y3m9DUK9W")));//$_f0 decode
//			  if (_$pF[_$po()] > 0 || _$n9 > 0 || _$qz > 0 || _$qk > 0) {
//			        _$nq(_$bf, _$ry(_$f3));
//			        _$nq(_$bf, _$ry(_$lv));
//			        _$nq(_$bf, _$ry(_$e4));
//			        _$nq(_$bf, _$ry(_$pD));
//			        _$nq(_$bf, _$ry(_$pq));
//			        _$nq(_$bf, _$ry(_$n9));
//			        _$nq(_$bf, _$ry(_$qz));
//			        _$nq(_$bf, _$ry(_$qk));
//			        _$nq(_$bf, _$ry(_$oE));
//			        _$nq(_$bf, _$ry(_$mD));
//			        _$nq(_$bf, _$ry(_$pP));
//			        _$mE |= 2;
//			    }
            bos.write(ry(0));
            bos.write(ry(1));
            bos.write(ry(1));
            bos.write(ry(0));
            bos.write(ry(1));
            bos.write(ry(0));
            bos.write(ry(0));
            bos.write(ry(1));
            bos.write(ry(2));
            bos.write(ry(0));
            bos.write(ry(24));

            // if $_fh0存在则decode加入
            // $_f1 decode
            bos.write(ia2ba(decode("t5vkmXwuUtqfnaX42LaFxHN6SJ9")));//t5vkmXwuUtqfnaX42LaFxHN6SJ9
            bos.write(ia2ba(decode("TkQi9w.3ZGL1mM02bjmmZyQxEHL")));//$_fr decode  TkQi9w.3ZGL1mM02bjmmZyQxEHL
            // if wC wb存在，加入
            bos.write(ry(0));
            bos.write(ry(0));

            //_$bf.push(_$qo);
            bos.write(new byte[]{100, 0, 0, 0});

            // 时间差 当前时间- 1502283301789=456
            Random r = new Random(System.currentTimeMillis());
            int tsMills = 109200 + r.nextInt(500);
            bos.write(new byte[]{1, -1});

            //$fr lJeWuFj6GQBzHh9cL81cQeZnwuV
            bos.write(ia2ba(decode("OFv0KULEvu2QO6E8DyPAqX4Hb8V")));

            // vK 8位数组， tk = 0
            //lJeWuFj6GQBzHh9cL81cQeZnwuV
            //127, 120, 96, 8, 231, 162, 57, 102
            //(byte)196,2,(byte) 64,63,(byte) 201,(byte) 162,56,102
            byte[] vK = new byte[]{127, 120, 96, 8, (byte) 231, (byte) 162, 57, 102};
            byte tk = (byte)'2';
            bos.write(vK);
            bos.write(tk);
            //$_fpn1 undefine

            //var _$fO = _$mX();
            bos.write((byte)27);

            //$_vvCI = atGkba
            bos.write(ia2ba(decode("atGkba")));

            //$_JQnh = 6BqrkA
            bos.write(ia2ba(decode("6BqrkA")));

            //_$pK 需加断点调试一下
//			tsMills = 4560 + r.nextInt(2000) + 200;
            bos.write(new byte[]{1, -1});

            int ow = 48127;// 根据上面的存在关系得到的位图
            byte[] blen = int2byte(new int[]{ow});
            byte[] result = bos.toByteArray();
            for(int i = 0; i < 4; i++)
            {
                result[2 + i] = blen[i];
            }

            return result;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new byte[0];
//		list.add(e)
    }

    public static byte[] sha1(String content)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes("UTF-8"));
            return digest;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    public static byte[] sha1(byte[] content)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content);
            return digest;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    public int _tK(int ai) {
        int[] _$j5 = new int[]{0, 1, 3, 7, 0xf, 0x1f};
        return (ai >> ft) | ((ai & _$j5[ft]) << (6 - ft));
    }

    public String tkConst(int index)
    {
        int rindex = index % 64;
        int mv = index - rindex;
        int j5 = _tK(rindex);
        j5 ^= ou;
        mv += j5;
        return cst[mv];
    }

    public String[] getConstant(String content)
    {
        getbKey1();
        ContentTaker taker = new ContentTaker();
        taker.content = content;
        taker.off = 0;
        int len = taker.nextOff();
        String[] list = new String[len];
        int index = 0;
        while(taker.off < content.length())
        {
            int next = taker.nextOff();
            list[index] = content.substring(taker.getOff(), taker.getOff() + next);
//			System.out.println(list[index].length());
            taker.addOff(next);
            index++;
        }
        return list;
    }



    public static int[] long2int(long time)
    {
        int[] ta = new int[]{(int)(time / 0x100000000l) & 0xffffffff, (int)time & 0xffffffff};
        return ta;

    }

    public static byte[] int2byte(int[] ia)
    {
        byte[] li = new byte[ia.length*4];

        for (int i = 0; i < ia.length; i++) {
            int _$iC = ia[i];
            li[i*4] = (byte)((_$iC >>> 24) & 0xFF);
            li[i*4 + 1] = (byte) ((_$iC >>> 16) & 0xFF);
            li[i*4 + 2] = (byte) ((_$iC >>> 8) & 0xFF);
            li[i*4 + 3] = (byte) (_$iC & 0xFF);
        }
        return li;
    }


    public static void main(String[] args)
    {
        byte a = -113;
        System.out.println((byte)(a>>>2));
        CPSDecoder login = new CPSDecoder();
        login.testAESKey();
        login.getbKey1();
//		System.out.println(login.getConstant("{qqKEKXOZhulcfIIZkt.Rke8hqqqqqqqqqqIxfTV8mw.ACS11VEQYVeYs02ks0zyAVVqhcTZZMsfAimEqthq9kIAxXAaqc80qDc5c656q}ERLSVz2teUHakdfHymHpEZAIakBlDNTnBVj0E5pkaqI71er1nmBls5AdSmIzrZG4JrQGpSpkqpXqtSfUqAIpEuGH3rwgH6phocQmEaSMwkZRi2lFss5yV_aFDUEN1GaOMlJEr6ph1mxqK0Sivk_ZY4qjbctW1.Aqm26169qkcf9qkh_Aq8c5EbTsdUHeQnTkvIiel46OH3Wz0uCqq|:JTaWMGYXDKZDMf23DYqcKqeskm3mYf2Ur23RVr2MrTqDKGwVrT3mUqprr9lDUaeQrVWJpARrq0VWlVEKDpNFoY3KV2xVl2Nlq07lXTo0KFSV42k2KXqRyYOjkMqo72odKFYFyYkgD8TRzA5Gkim3glHnK4NKXT.dViER5TbOq_glLlIup82KZfv5DQT8S9tWYW0Jvq88pWyKTYtDYWpAnT.iDQe3aqCIrNZHGqucUeJmTfvUKzxKCTtRYJwFGaFhqgZlf0sYY7S19YjNVhARLYvyrBVodAIepteKX2hvKjJABfbBKIQDdaH4ri7qhCYa3qcxtvAuqJ1502282686009qqqYrvQJ9yU4eqkGiCri21iGFqr0qhV9BlwHAz_rhqk162qD262216qqqqqqqqqqqqqr0qVlOlOrcEqxG3fqc3WtGWP"));
//		System.out.println(login.parseConst4a("V9JiVTWuYUfmhbNu`hupp32R0AVpPM9SK1KWBwurDJTpjV0Sf31yjQa`7JBnPN_EDM.UHS70VHrua4_8mAmadnm8_643Ng6efVZ`PIxhiHRkNu1znh3G9HFH084ZuzokjHxk_OBHMpYlEaE"));
//		login.decode("v..3SJvUyGpntJplLLfALbEvtTrpIpE_Ok4m4d5ExfkI.nyB5CjY0rHpcfkGPA9HjGyjjgW4afS_.Qx2yHBo5607.1jK7707hBRsWgh3gRv4F1QiKKQr9z9I2BEK97vjFux1sGGdwnZR_4vrC_x4Ty8ImWlHfJyQExmyjRdj9tk8VzIQ9ztVokA07QdqobRJDBMPA3CP0q9LLTf6oqktrG6Hi7m3jP3vbtYix.D2jy83cEcknQT0t0a7mmouSqCtceulKxX_cGn7ZngEJARtNaZ");
//		int[] ta = long2int(1502108829039l);
//		byte[] ba = int2byte(ta);
//		System.out.println(ta[0]  + "" + ta[1]);
//		System.out.println(rnd(61486));
        System.out.println(login.xE(new byte[]{71, 25, 59, 112, 121, 75}));
//		System.out.println(sha1(new byte[]{63,78,52,(byte)174,(byte) 172,(byte) 198,(byte) 205,61,(byte) 221,6,93,119,(byte) 172,30,(byte) 157,86,(byte) 255,103,(byte) 196,(byte) 148,(byte) 166,(byte) 190,13,53,14,(byte) 159,(byte) 156,23,30,(byte) 215,(byte) 236,(byte) 165}));
//		byte[] bb = sha1("/t.do?requestid=getverificationcode");
//		System.out.println(login.process80T(4, "1hSFc1U1o3m9IbgYaU9ewScCiP8F1ynRQmK5tkhNGR_klw5QPs5e5TQzgU.sp6lavnw6VWFqdyKEgx4l_rRMGUB9vy9Uhaxd7Y0d4txS7XiCEKx6BPq1AmgM.ub3oGuwdlMs3TkH59cJVn1ANWvXX65XRWtzBeMaRUTU8GYV798tOb3f3BLl0OPdKMGdY_7xr5rwpMsoB33HKXB.ZiPAT.RuX.sXZFZSR9VnsM3oABM3Th8dQQO7MWvYw0IZfdm9YeRPttjXJBsqjwNuj_18IZnG"));
        System.out.println(new Date(1513238085194l));
    }

    private byte[] ry(int pJ)
    {
        if(pJ > 0xFFFF)
        {
            pJ = 0xFFFF;
        }

        return new byte[] {(byte)((pJ & 0xFF00) >> 8), (byte)(pJ & 0xFF)};
    }

    public int vq(byte[] input)
    {
        byte[] vqkey = new byte[256];
        int ic = 0;
        for(int i = 0; i < input.length; i++)
        {
            ic = vqkey[(ic ^ input[i]) & 0xFF];
        }

        return ic;
    }

    public String xE(byte[] ba)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ba.length; i = i + 3)
        {
            sb.append(key2.charAt(((ba[i] & 0xFF) >>> 2)));

            if(i + 1 < ba.length)
            {
                sb.append(key2.charAt(((ba[i] & 3) << 4) | ((ba[i + 1] & 0xFF) >>> 4)));
            }
            else
            {
//				System.out.println("ba len:" + ba.length);
//				System.out.println(key2.charAt(((ba[i] & 3) << 4)));
                sb.append(key2.charAt(((ba[i] & 3) << 4)));
            }

            if(i + 1 < ba.length)
            {
                if(i + 2 < ba.length)
                {
                    sb.append(key2
                            .charAt((((ba[i + 1] & 15) << 2) | ((ba[i + 2] & 0xFF) >>> 6)) & 0xFF));
                }
                else
                {
                    sb.append(key2
                            .charAt((((ba[i + 1] & 15) << 2)) & 0xFF));
                }
            }

            if(i + 2 < ba.length)
            {
                sb.append(key2.charAt(ba[i + 2] & 63));
            }
        }
        return sb.toString();
    }

    public class ContentTaker
    {
        private int off;

        private String content;

        public int getOff()
        {
            return off;
        }

        public void addOff(int next)
        {
            off += next;
        }

        public int nextOff()
        {
            int be = 0;
            int a0 = bkey1[content.charAt(off)];
            if(a0 < 0)
            {
                off++;
                be = off + 3;
                a0 = 0;
                for(;off < be; off++)
                {
                    a0 = a0 * 86 + bkey1[content.charAt(off)];
                }
            }
            else if (a0 < 64)
            {
                off++;
            }
            else if (a0 <= 86)
            {
                a0 = (a0 - 64) * 86 + bkey1[content.charAt(off + 1)] + 64;
                off += 2;
            }

            return a0;
        }
    }

    public void parseMetaContent(String content)
    {
        //从meta content获取keyPre
    }

    private static int i = 0;

    // FSSBBIl1UgzbN7N80T
    public String process80T(int rd, String fssb80t, byte[] aeskey, long serverTime, long curTime)
    {
        getbKey1();
        char first = fssb80t.charAt(0);
        int[] gc = decode(fssb80t.substring(1));
        byte[] sh = ia2ba(slice(gc, 0, 65));
        int bN = gc[65];
        for (int i = 0; i < 65; i++)
        {
            sh[i] ^= bN;
        }

        i++;
        long p8 = serverTime + System.currentTimeMillis() + i - curTime;
        int[] ta = long2int(p8);
        byte[] d5 = int2byte(ta);
//		byte[] ro = pushInfo(rd);//4,6
        byte[] ro = new byte[]{1, (byte)rd, 0, 0, -69, -1, 1, 1, 0, 0, 0, 0, 0, 2, 31, 1, -35, 1, 82, 123, -37, 104, 84, -80, 16, 68, 70, 106, 20, -46, -126, -92, 109, 11, 40, -60, 16, -85, -113, 45, 53, -126, 21, -46, -18, 117, 10, 2, -83, -114, 88, 22, 65, -105, 90, 67, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 95, 43, -23, -61, 22, -25, 115, 92, -96, 33, -53, 11, -69, -106, -116, 26, 36, -6, 119, -60, -39, -44, 55, 14, -111, -38, -40, -14, 10, 18, 21, -67, 37, -102, -95, 69, -13, -105, 9, 32, -6, 0, 0, 0, 0, 100, 0, 0, 0, 15, -1, -115, -87, -12, 89, 122, 8, -97, 57, 92, -113, 114, 30, 27, -104, -112, 2, -18, -49, -103, -27, 127, 120, 96, 8, -25, -94, 57, 102, 5, 27, 10, -88, 10, 118, -34, -16, 1, 13, 15, -1};
        byte[] rE = concat(d5, ro);
        int r0 = vp(concat(sh, rE));
        for (int i = 0; i < 65; i++)
        {
            sh[i] ^= r0;
        }



        byte[] randomIv = new byte[16];
        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i < 16; i++)
        {
            randomIv[i] = (byte)r.nextInt(255);
        }

        byte[] aesResult = AES.Encrypt(aeskey, randomIv, rE);
        aesResult = concat(randomIv, aesResult);

        byte[] concatResult = concat(sh, new byte[]{(byte)r0});
        concatResult = concat(concatResult, aesResult);
        String rest = xE(concatResult);
        String result = first + rest;
        return result;
    }

    public byte[] getAESKEY2(String key2, int b)
    {
        byte[] key2b = ia2ba(decode(key2));
        for(int i = 0; i < key2b.length; i++)
        {
            key2b[i] = (byte)((key2b[i] & 0xFF) ^ (b & 0xFF));
        }
        byte[] aeskey2 = sha1(key2b);
        return aeskey2;
    }

    public byte[] getAESKEY3(String key2, int b)
    {
        byte[] key2b = ia2ba(decode(key2));
        for(int i = 0; i < key2b.length; i++)
        {
            key2b[i] = (byte)((key2b[i] & 0xFF) ^ (b & 0xFF));
        }
//		byte[] aeskey2 = sha1(key2b);
        return key2b;
    }

    public void testAESKey()
    {
        int b = 182;
        String key1 = "25lHRg3eBkGEVBo3.3cpIRErl937hII3UX6iOOYImIZ";
        byte[] key1b = ia2ba(decode(key1));
        for(int i = 0; i < key1b.length; i++)
        {
            key1b[i] = (byte)((key1b[i] & 0xFF) ^ (b & 0xFF));
        }
//		System.out.println(key1b);
    }

    public byte[] getAESKey(String key1, String key2, int b)
    {
        byte[] key1b = ia2ba(decode(key1));
        for(int i = 0; i < key1b.length; i++)
        {
            key1b[i] = (byte)((key1b[i] & 0xFF) ^ (b & 0xFF));
        }
        byte[] aeskey1 = sha1(key1b);
        // decode("K1f1uWj9jT5dRkZpnvudJx9rNBhq6DeUMH6_QUPSO7W");
        // 反调试,直接异或js取值然后sha1
        // 从cst取kp1Mt_znz0C + cst4a[0] + 从js爬_$GG
        // kp1Mt_znz0Cco9OfGthEGG
        // 取随机数,各种异或然后sha1
        byte[] key2b = ia2ba(decode(key2));
        for(int i = 0; i < key2b.length; i++)
        {
            key2b[i] = (byte)((key2b[i] & 0xFF) ^ (b & 0xFF));
        }
        byte[] aeskey2 = sha1(key2b);

        byte[] aeskey = new byte[32];
        for (int _$g3 = 0; _$g3 < 16; _$g3++) {
            aeskey[_$g3 * 2] = aeskey1[_$g3];
            aeskey[_$g3 * 2 + 1] = aeskey2[_$g3];
        }
        return aeskey;
    }

    public byte[] ia2ba(int[] ia)
    {
        byte[] ba = new byte[ia.length];
        for (int i = 0; i < ia.length; i++)
        {
            ba[i] = (byte) ia[i];
        }
        return ba;
    }

    public int[] ba2ia(byte[] ia)
    {
        int[] ba = new int[ia.length];
        for (int i = 0; i < ia.length; i++)
        {
            ba[i] = ia[i];
        }
        return ba;
    }

    public byte[] mq(int qP)
    {
        return new byte[0];
    }

    public static byte[] concat(byte[] src, byte[] input)
    {
        byte[] dest = new byte[src.length + input.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        System.arraycopy(input, 0, dest, src.length, input.length);
        return dest;
    }

    private byte[] _$jM() {
        byte[] _$nj = new byte[256];
        int _$nX = 0;
        for (int _$pl = 0; _$pl < 256; ++_$pl) {
            _$nX = _$pl;
            for (int _$k7 = 0; _$k7 < 8; ++_$k7) {
                if ((_$nX & 0x80) != 0)
                    _$nX = (_$nX << 1) ^ 7;
                else
                    _$nX <<= 1;
            }
            _$nj[_$pl] = (byte)(_$nX & 0xff);
        }
        return _$nj;
    }

    public int vp(byte[] input)
    {
        byte[] vqkey = _$jM();
        int ic = 0;
        for (int i = 0; i < input.length; i++)
        {
            ic = vqkey[(ic ^ input[i]) & 0xFF];
        }

        return ic;
    }

    public static int[] slice(int[] src, int start, int end)
    {
        int len = end - start;
        int[] dest = new int[len];
        System.arraycopy(src, start, dest, 0, len);
        return dest;
    }

    public static byte[] slice(byte[] src, int start, int end)
    {
        int len = end - start;
        byte[] dest = new byte[len];
        System.arraycopy(src, start, dest, 0, len);
        return dest;
    }

    public static byte rnd(int rz)
    {
        if (rz < 0xE0)
            return (byte)rz;
        return (byte)((int)(Math.log(rz) / Math.log(2) + 0.5) | 0xE0);
    }

    public int[] decode(String cc)
    {
        JSDecoder1 dec = new JSDecoder1();
        return dec._$nP(cc);
    }
}

