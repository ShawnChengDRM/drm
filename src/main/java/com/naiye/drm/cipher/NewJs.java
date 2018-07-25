package com.naiye.drm.cipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class NewJs
{
    private byte[] key2 = new byte[] { 12, 3, 5, 56, (byte) 178, (byte) 131, 7,
            10, 12, 5, (byte) 217, 15, 3, 2, 14, 13 };

    public String encKey(String aesKey, String key)
    {
        CPSDecoder cps = new CPSDecoder();
        JSDecoder1 jsd = new JSDecoder1();
        byte[] aeskey = cps.ia2ba(cps.decode(aesKey));
        byte[] content = cps.ia2ba(cps.decode(key));
        byte[] key2s = cps.slice(aeskey, 0, 16);
        int[] key2sint = cps.ba2ia(aeskey);
        _$ti(key2sint);
        key2s = cps.ia2ba(key2sint);
        aeskey = cps.concat(aeskey, key2s);
        aeskey = cps.sha1(aeskey);
        aeskey = cps.slice(aeskey, 0, 16);

        byte[] iv = cps.slice(content, 0, 16);
        byte[] realContent = cps.slice(content, 16, content.length);
        byte[] aesResult = AES.Decrypt(aeskey, iv, realContent);
        String result = jsd._$lU(cps.ba2ia(aesResult));
        System.out.println(result);
        return result;
    }

    public static byte[] encrypt(byte[] datasource, String password)
    {
        try
        {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)
    {
        NewJs njs = new NewJs();
        njs.encKey("KwbPg.tksuYl6e2jeHLgKA",
                "n1V.8_247iTFPYwHGycLM.hb0HZ.Z6gLmo3hYNDju6tGS38XJU5tY_8LgpUtWSiT");
    }

    public int _$rn(int[] _$hC) {
        int _$rr = _$r2();
        int _$qi = _$th();
        if (_$fK() != 0) {
            _$hC[_$kC(_$o8(), 16)] = _$ak();
        }
        _$aE(_$hC);
        return _$ak();
    }

    public int _$aE(int[] _$hC) {
        int _$rr = _$fK();
        _$rr = _$ts();
        int _$aS = _$rg();
        int _$qi = _$tk();
        _$hC[15] = _$th();
        _$rr = _$db();
        return _$o8();
    }

    public int _$r2() {
        return 5;
    }

    public int _$ti(int[] _$hC) {
        _$hC[0] = _$qy(_$hC);
        _$hC[_$kC(_$hC[_$kC(_$ak() + _$rg(), 16)], 16)] = _$tq(_$hC);
        if (_$hC[_$kC(_$db() + _$o8(), 16)] != 0) {
            _$rn(_$hC);
        }
        _$hC[1] = _$hC[_$kC(_$ak() + _$rg(), 16)];
        return _$sb(_$hC);
    }

    public int _$sb(int[] _$hC) {
        int _$qi = _$ss();
        _$qi = _$db();
        int _$rr = _$sJ();
        int _$aS = _$tk() + _$r2();
        _$qi = _$db() + _$o8();
        _$gg(_$hC);
        _$hC[_$kC(_$hC[_$kC(_$rM(), 16)], 16)] = _$to(_$hC);
        return _$th();
    }

    public int _$to(int[] _$hC) {
        int _$aS = _$r2();
        int _$qi = _$th();
        _$hC[_$kC(_$fK(), 16)] = _$ts();
        _$aS = _$rg();
        int _$rr = _$tk();
        return _$r2();
    }

    public int _$tc(int[] _$hC) {
        _$hC[14] = _$ql();
        _$hC[_$kC(_$ss(), 16)] = _$db();
        int _$aS = _$sJ();
        _$aS = _$pz();
        return _$rN();
    }

    public int _$gg(int[] _$hC) {
        _$tc(_$hC);
        _$hC[12] = _$sS();
        int _$aS = _$o8();
        int _$qi = _$ak();
        _$aS = _$rN();
        _$aS = _$ql();
        _$kG(_$hC);
        return _$hC[_$kC(_$fK(), 16)];
    }

    public int _$kG(int[] _$hC) {
        _$hC[8] = _$ts();
        _$hC[_$kC(_$ak(), 16)] = _$rg();
        _$hC[9] = _$r2();
        return _$th();
    }

    public int _$tq(int[] _$hC) {
        int _$qi = _$ts();
        _$qi = _$tk();
        if (_$th() != 0) {
            int _$rr = _$rM();
        }
        _$hC[_$kC(_$sS(), 16)] = _$fK();
        _$hC[_$kC(_$o8(), 16)] = _$ak();
        int _$rr = _$tk();
        return _$hC[_$kC(_$ql(), 16)];
    }

    public int _$qy(int[] _$hC) {
        _$rE(_$hC);
        int _$rr = _$sS();
        if (_$rM() != 0) {
            _$hC[_$kC(_$rg(), 16)] = _$tk();
        }
        _$hC[6] = _$rM();
        _$hC[2] = _$pz();
        _$sX(_$hC);
        return _$qR(_$hC);
    }

    public int _$qR(int[] _$hC) {
        int _$rr = _$o8();
        _$rr = _$ak();
        _$hC[_$kC(_$pz(), 16)] = _$rN();
        _$hC[12] = _$sS();
        return _$fK();
    }

    public int _$fK() {
        return 0;
    }

    public int _$sX(int[] _$hC) {
        _$hC[_$kC(_$th(), 16)] = _$ss();
        int _$qi = _$rM();
        int _$aS = _$sJ();
        _$hC[0] = _$rN();
        return _$ql();
    }

    public int _$tk() {
        return 2;
    }

    public int _$ql() {
        return 14;
    }

    public int _$sJ() {
        return 9;
    }

    public int _$ak() {
        return 15;
    }

    public int _$rg() {
        return 13;
    }

    public int _$ts() {
        return 12;
    }

    public int _$rM() {
        return 4;
    }


    public int _$rN() {
        return 1;
    }

    public int _$rE(int[] _$hC) {
        int _$aS = _$db();
        int _$rr = _$o8();
        int _$qi = _$pz();
        _$qi = _$rN();
        _$hC[_$kC(_$th(), 16)] = _$ss();
        return _$db();
    }

    public int _$kC(int _$rP, int _$hC) {
        return Math.abs(_$rP) % _$hC;
    }


    public int _$sS() {
        return 3;
    }

    public int _$ss() {
        return 7;
    }

    public int _$pz() {
        return 8;
    }

    public int _$o8() {
        return 11;
    }

    public int _$db() {
        return 10;
    }

    public int _$th() {
        return 6;
    }
}
