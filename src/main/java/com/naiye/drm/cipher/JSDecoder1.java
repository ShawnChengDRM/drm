package com.naiye.drm.cipher;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class JSDecoder1
{
    private String key1 = "ef ghi  jklmnoL U3F9\\_XM?Ep  q rs1PW\');A0@.7I<JDC=:RV85-O6]t uv[ QG#`^BY,/K$%&S(2!\"4+TH>*ZNacbd";

    private String _$w0 = "7-\"HC.c'Zp(gnfU)8$GL&M<Kik>Y=S|9}z0Ij/yE?mQavxwOF%u 2AoT:DRr\\!^X`1b,NP;tl4][3~_*e5+q@shJW#{dBV6";

    private String key2 = "qrcklmDoExthWJiHAp1sVYKU3RFMQw8IGfPO92bvLNj.7zXBaSnu0TC6gy_4Ze5d{}|~ !#$%()*+,-:=?@[]^";

    private String _$oq = "S]\"y1Q4'*/orc!%EBaTliq~ 0?Xf6<:HtU|$YGP2#Cw735dOeJZ=W.}x_;(k\\j^n`Kb&{p-+v8D9hI,V)s[ugRLNMFAz>@m";//第一版的_$xt

    private byte[][] _$oX = new byte[6][];

    private byte[] _$xt1 = new byte[256];

    private String _$cU_$oX;

    private int _$qY = 0;

    private int _$hE = 0;

    private String _$pa;

    private int _$pK;

    private int[] _$pa1;

    private String[][] _$qL = new String[5][];

    public String _$cU()
    {
        return _$cU_$oX.substring(_$qY);
    }

    public static String $rB(int _$pU, int _$dL, int _$lR, String[] storage) {
        int _$qu = _$pU % 64;
        int _$qz = _$pU - _$qu;
        _$qu = _$mT(_$qu, _$dL);
        _$qu ^= _$lR;
        _$qz += _$qu;
        return storage[_$qz];
    }

    public static int _$mT(int _$ks, int _$dL) {
        int[] _$pE = new int[]{0, 1, 3, 7, 0xf, 0x1f};
        return (_$ks >> _$dL) | ((_$ks & _$pE[_$dL]) << (6 - _$dL));
    }

    private void  _$oy() {
        String _$cU = _$jx();
        String _$oX = _$jx();
        String[] _$cU1 = _$oB(_$cU).split("`");
        String[] _$oX1 = _$oB(_$oX).split("`");
//	    _$nQ(_$cU, _$oX);
    }

    public int _$oq()
    {
        char _$pa = _$cU_$oX.charAt(_$qY);
        if (_$pa >= 40)
        {
            _$qY++;
            return _$pa - 40;
        }
        int _$m6 = 39 - _$pa;
        _$pa = 0;
        for (int _$qd = 0; _$qd < _$m6; _$qd++)
        {
            _$pa *= 87;
            _$pa += _$cU_$oX.charAt(_$qY + 1 + _$qd) - 40;
        }
        _$qY += _$m6 + 1;
        return _$pa + 87;
    }

    public String _$pZ()
    {
        int _$m6 = _$oq();
        String _$qd = _$cU_$oX.substring(_$qY, _$m6 + _$qY);
        _$qY += _$m6;
        return _$qd;
    }

    public String _$cU1() {
        return _$cU_$oX.substring(_$qY);
    }

    public String _$jx() {
        return _$pZ();
    }

    public void _$av()
    {
//		_$cU_$oX = FileUtil.readContent("D:/content.txt");
        try
        {
            _$cU_$oX = StringEscapeUtils.unescapeHtml4(_$cU_$oX);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        _$qY = 0;
    }

    public String[] _$b4(String jsContent, String jsKeyContent)
    {
        getKeyOx();
        _$cU_$oX = jsContent;
        _$av();
        _$oy();
        _$jx(); _$jx(); _$jx(); _$jx(); _$jx(); _$jx();
        String _$mB = jsKeyContent;
        String[] _$qY = _$kE();
        int[] _$oX = _$oc();
        int[] _$oq = _$oc();
        System.out.println(_$oq[0]);
        String[] result = new String[6];
        result[0] = "" + _$oq[0];
        result[4] = "" + _$oq[2];
        result[5] = "" + _$oq[1];
        if (_$mB != null) {
            _$pa = _$mB;
            result[1] = _$cS(_$mB, _$qY, _$oX);
            result[2] = _$oB(_$jx());
            result[3] = _$cU1();
        }
//	    String abc = _$cU1();
//	    CodeTaker taker = new CodeTaker(abc);
//	    String[] p6 = taker.getP6();
//	    System.out.println(p6.length);
        return result;
    }

    public byte _$qt(String _$pN) {
        byte[] _$ll = _$ch()[5];
        return _$ll[_$pN.charAt(0)];
    }

    private static void printls(String[] ls)
    {
        for(String s : ls)
        {
            System.out.println(s);
        }
    }

    public String _$oB(String _$pu) {
        int _$cU, _$qY = _$pu.length();
        JSArray<Character> _$pZ = new JSArray<>();
        int _$oX = _$pu.charAt(0) - 40;
        for (int _$oq = 1; _$oq < _$qY; _$oq++) {
            _$cU = _$pu.charAt(_$oq);
            if (_$cU >= 40 && _$cU < 127) {
                _$cU += _$oX;
                if (_$cU >= 127) _$cU = _$cU - 87;
            }
            _$pZ.push(Character.valueOf((char)_$cU));
        }
        return _$pZ.join();
    }

    private String _$nV(String _$if) {
        int _$rA = _$if.length();
        byte[] _$dY = new byte[_$rA];
        int _$cl;
        for (int _$rE = 0; _$rE < _$rA; _$rE++) {
            _$cl = _$if.charAt(_$rE);
            if (_$cl >= 32 && _$cl < 127) _$dY[_$rE] = (byte)_$w0.charAt(_$cl - 32);
            else _$dY[_$rE] = (byte)_$if.charAt(_$rE);
        }
        return new String(_$dY);
    }

    public int[] _$oc() {
        String[] _$cU = _$oB(_$jx()).split("`");
        int[] _$cU1 = new int[_$cU.length];
        for (int _$oX = 0; _$oX < _$cU.length; _$oX++) _$cU1[_$oX] = Integer.parseInt(_$cU[_$oX]);
        return _$cU1;
    }

    private void _$pE() {
        if (_$hE == -1) return;
        if (_$hE == 0) {
            _$pK++;
            if (_$pa.charAt(_$pK) == '1') {
                _$pK++;
            } else if (_$pa.charAt(_$pK) == '0') {
                _$hE = -1;
                _$pK++;
                return;
            } else {}
        }


    }

    public void getKeyOx()
    {
        byte[] _$qY = _$oX[0] = new byte[127];
        byte[] _$oq = _$oX[1] = new byte[127];
        byte[] _$m6 = _$oX[2] = new byte[127];
        byte[] _$pZ = _$oX[3] = new byte[127];
        byte[] _$qi = _$oX[4] = new byte[127];
        byte[] _$cU = _$oX[5] = new byte[255];
        for(int i = 0; i < 255; i++)
        {
            _$cU[i] = -1;
        }

        for (int _$qd = 0; _$qd < key2.length(); _$qd++) {
            int _$pa = key2.charAt(_$qd);
            _$qY[_$pa] = (byte) (_$qd << 2);
            _$oq[_$pa] = (byte) (_$qd >> 4);
            _$m6[_$pa] = (byte) ((_$qd & 15) << 4);
            _$pZ[_$pa] = (byte) (_$qd >> 2);
            _$qi[_$pa] = (byte) ((_$qd & 3) << 6);
            _$cU[_$pa] = (byte) _$qd;
        }
    }

    private byte[][] _$ch()
    {
        if(_$oX[0] != null)
        {
            return _$oX;
        }

        getKeyOx();
        return _$oX;
    }
    //	function _$f9(_$ak, _$pX) {
//	    var _$am = _$qr()[5];
//	    var _$q8 = _$am[_$ak[_$lu()](_$pX)];
//	    if (_$q8 < 82) return 1;
//	    return 86 - _$q8 + 1;
//	}
    private int _$ak(String _$qY, int _$pZ) {
        byte[] _$cU = _$ch()[5];
        int _$oq = _$cU[_$qY.charAt(_$pZ)];
        if (_$oq < 82) return _$oq;
        int _$oX = 86 - _$oq;
        _$oq = 0;
        for (int _$pu = 0; _$pu < _$oX; _$pu++) {
            _$oq *= 86;
            _$oq += _$cU[_$qY.charAt(_$pZ + 1 + _$pu)];
        }
        return _$oq + 82;
    }

    private int _$mO(String _$oX, int _$oq) {
        byte[] _$cU = _$ch()[5];
        int _$pu = _$cU[_$oX.charAt(_$oq)];
        if (_$pu < 82) return 1;
        return 86 - _$pu + 1;
    }

    private int _$qG() {
        int _$nB = _$ak(_$pa, _$pK);
        _$pK += _$mO(_$pa, _$pK);
        return _$nB;
    }

    public String[] _$kE() {
        String _$pu = _$oB(_$jx());
        String[] _$pu1 = _$aU(_$pu, 2).toArray(new String[0]);
        String _$oX = _$nV("_$");
        for (int _$cU = 0; _$cU < _$pu1.length; _$cU++) {
            _$pu1[_$cU] = _$oX + _$pu1[_$cU];
        }
        return _$pu1;
    }

    private String _$qq(int _$qf) {
        int _$nB = _$pK;
        _$pK += _$qf;
        return _$pa.substring(_$nB, _$pK);
    }

    private String[] _$i1(boolean _$qC) {
        int _$qb, _$nB, _$qx;
        _$pE();
        _$nB = _$qG();
        _$qb = _$qG();
        String _$qa = _$qq(_$qb);
        if (_$nB == 0 && _$qb == 0) return new String[0];
        String[] _$qf = _$qa.split("`");
        if (_$qC) {
            for (int _$gB = 0; _$gB < _$nB; _$gB++) {
                _$qf[_$gB] = _$ez(_$qf[_$gB]);
            }
        }
        return _$qf;
    }

    public void getXt1()
    {
        for(int i = 0; i < 32; i++)
        {
            _$xt1[i] = (byte)i;
        }

        for(int i = 32; i < 127; i++)
        {
            _$xt1[i] = (byte)_$oq.charAt(i-32);
        }

        for(int i = 127; i < 256; i++)
        {
            _$xt1[i] = (byte)i;
        }
    }

    private void _$pl(JSArray<String> _$gB) {
        int _$qb, _$qf;
        int[] tmp = _$qx();
        _$qb = tmp[0];
        _$qf = tmp[1];
        if (_$qb <= 4) {
            _$gB.push(_$qL[_$qb][_$qf]);
        } else {
            if (_$qb == 6) {
                for (int _$nB = 0; _$nB < _$qf; _$nB++) {
                    _$pl(_$gB);
                }
            } else if (_$qb == 5) {
                _$m6(_$qf, _$gB);
            } else if (_$qb == 7) {
//	                _$cU();
            } else {}
        }

    }

    private void _$m6(int _$gB, JSArray<String> _$qb) {
        int _$nB = _$qy(),
                _$qa;
        JSArray<JSArray<String>> _$ov = new JSArray<>();
        JSArray<JSArray<String>> _$qf = new JSArray<>();
        JSArray<String>_$qx;
        if (_$gB == 3) {
            //未知
        }
        for (_$qa = 0; _$qa < _$nB; _$qa++) {
            _$qx = new JSArray<>();
            _$pl(_$qx);
            _$qf.push(_$qx);
        }
        for (_$qa = 0; _$qa < _$gB; _$qa++) {
            _$qx = new JSArray<>();
            _$pl(_$qx);
            _$ov.push(_$qx);
        }

        int _$no = 0,
                _$qC = 0;

        while (_$no < _$nB) {
            _$bX(_$qb, _$qf.toArray(new JSArray[0])[_$no].toArray(new Object[0]));
            _$no++;
        }
        while (_$qC < _$gB) {
            _$bX(_$qb, _$ov.toArray(new JSArray[0])[_$qC].toArray(new Object[0]));
            _$qC++;
        }
    }

    private void _$bX(JSArray<String> _$oX, Object[] _$pu) {
        for (int _$cU = 0; _$cU < _$pu.length; _$cU++) {
            _$oX.push((String)_$pu[_$cU]);
        }
    }

    private int _$aQ() {
        return _$pa1[_$pK++];
    }

    private int _$qy() {
        int _$nB = _$pa1[_$pK];
        if ((_$nB & 0x80) == 0) {
            _$pK += 1;
            return _$nB;
        }
        if ((_$nB & 0xc0) == 0x80) {
            _$nB = ((_$nB & 0x3f) << 8) | _$pa1[_$pK + 1];
            _$pK += 2;
            return _$nB;
        }

        return 0;
    }

    private int[] _$qx() {
        int _$qb = _$aQ();
        int _$qf = _$qb & 0x1F;
        _$qb = _$qb >> 5;
        if (_$qf == 0x1f) {
            _$qf = _$qy() + 31;
        }

        return new int[]{_$qb, _$qf};
    }


    private String _$oo(String _$pZ) {
        getXt1();
        int _$qY = _$pZ.length();
        char[] _$cU = new char[_$qY];
        int _$pu = 0;
        int _$oX = 0;
        byte[] _$oq = _$xt1;
        for (_$pu = 0; _$pu < _$qY; _$pu++) {
            _$oX = _$pZ.charAt(_$pu);
            _$cU[_$pu] = (char)_$oq[_$oX];
        }
        return new String(_$cU);
    }

    private String _$ez(String _$oX) {
        int[] _$cU = _$nP(_$oX);
        return _$lU(_$cU);
    }

    public int[] _$nP(String _$oq) {
        int _$pa = _$oq.length();
        int len = _$pa * 3 / 4;
//	    if(len % 4 != 0)
//	    {
//	    	len = len + 1;
//	    }
        int[]_$i1 = new int[len];
        int _$qz, _$pl, _$hE, _$oT;
        int _$m6 = 0,
                _$qd = 0,
                _$pu = _$pa - 3;
        byte[][] _$oX = _$ch();
        byte[] _$pE = _$oX[0],
                _$qk = _$oX[1],
                _$pZ = _$oX[2],
                _$qY = _$oX[3],
                _$qi = _$oX[4],
                _$cU = _$oX[5];
        for (_$m6 = 0; _$m6 < _$pu; _$m6 = _$m6 + 4) {
            _$qz = _$oq.charAt(_$m6);
            _$pl = _$oq.charAt(_$m6 + 1);
            _$hE = _$oq.charAt(_$m6 + 2);
            _$oT = _$oq.charAt(_$m6 + 3);
            _$i1[_$qd++] = (_$pE[_$qz] & 0xFF) | (_$qk[_$pl] & 0xFF);
            _$i1[_$qd++] = (_$pZ[_$pl] & 0xFF) | (_$qY[_$hE] & 0xFF);
            _$i1[_$qd++] = (_$qi[_$hE] & 0xFF) | (_$cU[_$oT] & 0xFF);
        }
        if (_$m6 < _$pa) {
            _$qz = _$oq.charAt(_$m6);

            if(_$m6 + 1 < _$pa)
            {
                _$pl = _$oq.charAt(_$m6 + 1);
                _$i1[_$qd++] = (_$pE[_$qz] & 0xFF) | (_$qk[_$pl] & 0xFF);
            }
            else
            {
                _$i1[_$qd++] = (_$pE[_$qz] & 0xFF);
            }

            if (_$m6 + 2 < _$pa) {
                _$pl = _$oq.charAt(_$m6 + 1);
                _$hE = _$oq.charAt(_$m6 + 2);
                _$i1[_$qd++] = (_$pZ[_$pl] & 0xFF) | (_$qY[_$hE] & 0xFF);
            }
        }
        return _$i1;
    }

    public static String _$lU(int[] le)
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

    private JSArray<String> _$aU(String _$if, int _$rA) {
        int _$dY = _$if.length();
        String[]_$rE = new String[(int)Math.ceil(_$if.length() / _$rA)];
        int _$cl = 0;
        int _$aF = 0;
        for (; _$aF < _$dY; _$aF += _$rA, _$cl++) _$rE[_$cl] = _$if.substring(_$aF, _$aF + _$rA);
        return new JSArray<String>(_$rE);
    }

//	public String _$cS1(String _$pa, String[] _$qp, int[] _$qY)
//	{
//
//	}

    public String _$cS(String _$pa, String[] _$qp, int[] _$qY) {
        _$hE = 0;
        String _$pZ = "`";
        _$pE();
        int _$oq = _$qG();
        String[] _$qz = _$i1(false);
        String[] _$qk = _$i1(false);
        JSArray<String> tmp = new JSArray<String>(_$qk);
        tmp.push((new JSArray<String>(_$i1(true))));
        _$qk = tmp.toArray(new String[0]);
        String[] _$qi = _$i1(false);
        tmp = new JSArray<String>(_$qi);
        tmp.push((new JSArray<String>(_$i1(true))));
        _$qi = tmp.toArray(new String[0]);
        for (int _$qA = 0; _$qA < _$qi.length; _$qA++) {
            _$qi[_$qA] = _$oo(_$qi[_$qA]);
        }
        String[] _$qP = _$i1(false);
        tmp = new JSArray<String>(_$qP);
        tmp.push((new JSArray<String>(_$i1(true))));
        _$qP = tmp.toArray(new String[0]);
        _$pE();
        int _$gC = _$qG();
        _$pa1 = _$nP(_$pa.substring(_$pK));
        _$pK = 0;
        String[] _$qo = slice(_$qp,_$qY[1], _$qY[1] + _$qY[2]);
        String[] _$qd = slice(_$qp, 0, _$qY[0]);
//	    String[] _$qH = slice(_$qp, _$qY[9], _$qY[9] + _$qY[10]);
        String[] _$mT = slice(_$qp, _$qY[3], _$qY[3] + _$qY[4]);
        _$qL = new String[][]{_$qP, _$mT, new String[]{}, _$qd, _$qo};

        JSArray<String> _$oX = new JSArray<>();
        _$pl(_$oX);
        return _$oo(_$oX.join());
    }

    private String[] slice(String[] src, int start, int end)
    {
        return Arrays.copyOfRange(src, start, end);
    }
}
