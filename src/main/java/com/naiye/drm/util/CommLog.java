package com.naiye.drm.util;

import com.naiye.drm.cipher.HMACSHA1;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

import static com.naiye.drm.util.Tools.getUuid;


public class CommLog {

    public static void main(String[] args) throws Exception {

        String aaa = getCommLog(5561451);

        System.out.println(aaa);


    }

    public static String getCommLog(int index) throws Exception {

        String[] arg = new String[35];
        String uuid16 = getUuid().substring(0, 16);
        String uuid32 = getUuid();
        String uuid = getUuid();

        Random random = new Random();
        arg[0] = "E0111";// action
        arg[1] = "C80CBE7659A00001";//uuid16;// uuid16
        arg[2] = "1";
        arg[3] = "844";// method 后面自动赋值 不用管他
        arg[4] = "";
        arg[5] = "Win32";// osType
        arg[6] = "Netscape";// browserType
        arg[7] = "864*1536";// resolution
        arg[8] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
        arg[9] = "";
        //arg[10] = version;// version
        arg[10] = "1528215257466";//rdoInfo.getVersion();// version
        arg[11] = "3";
        arg[12] = "";
        arg[13] = "";
        arg[14] = "";
        arg[15] = "";
        arg[16] = "20180704105233843";//gettime();// currentTime
        //arg[17] = authId;// authId
        //arg[18] = netId;// netId
        arg[17] = "0789d61770844d658227d619e8d459a0";//rdoRequest.getAuthSessionId();// authId
        arg[18] = "e8036b8a86e84c939f1ee8fe64284129";//rdoInfo.getNetid();// netId
        arg[19] = "20180704105229696,20180704105233842";// field1
        arg[20] = "602,427";// field2
        arg[21] = "602,427";// field3
        arg[22] = "2503";// field4
        arg[23] = "C805636B8B900001FACD1115702015F1";//uuid32; // uuid32
        arg[24] = "";// sign
        arg[25] = "";
        arg[26] = "";
        arg[27] = "";
        arg[28] = "2503";
        arg[29] = "25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,26,26,27,28,30,30,30,30,31,31,32,32,32,32,32,32,32,34,34,34,34,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,36,36,36,36,36,36,36,36,36,39,39,42,43,44,46,46,46,46,47,48,48,50,50,50,50,50,49,48,48,48,48,48";
        arg[30] = "60,62,66,80,102,114,126,136,146,154,160,168,170,176,184,184,192,194,202,208,216,226,238,246,254,264,272,280,288,302,306,310,314,318,320,334,338,350,368,382,394,406,414,418,424,432,450,456,464,472,480,488,490,494,496,502,506,510,512,518,520,520,526,528,528,530,536,536,544,546,552,560,560,566,568,574,574,576,576,572,570,568,566,564,562,556,554,552,550,546,544";
        arg[31] = "89,7,11,4,18,6,9,10,7,8,9,8,8,9,9,8,8,9,8,8,7,16,9,8,8,10,7,9,7,18,8,9,8,9,9,15,8,8,18,7,9,8,9,8,9,8,17,8,8,9,7,19,6,9,8,8,10,8,8,16,66,10,8,8,8,8,10,7,165,10,9,7,10,7,9,16,8,35,15,8,9,9,22,74,38,49,104,55,16,137,41";
        arg[32] = "0";
        arg[33] = "";
        arg[34] = "";

/*        if (index == 1) {
            arg[4] = "";
            rdoRequest.setPolicyTime(arg[16]);
        } else if (index == 2) {
            arg[0] = "E0107";
            arg[19] = rdoRequest.getPolicyTime() + "," + arg[16];
            arg[20] = (random.nextInt(200) + 440) + "," + (random.nextInt(150) + 250);
        } else if(index == 3){
            arg[0] = "E0105";
            arg[19] = rdoRequest.getPolicyTime() + "," + arg[16];
//			arg[20] = (random.nextInt(200) + 440) + "," + (random.nextInt(150) + 250);
//			arg[21] = (random.nextInt(200) + 200) + "," + (random.nextInt(150) + 180);
//			arg[22] = 3000 + random.nextInt(8000) + "";
//			arg[27] = (450 + random.nextInt(150)) + "," + (150 + random.nextInt(150)) + ","
//					+ (150 + random.nextInt(300)) + "," + (100 + random.nextInt(700)) + ","
//					+ (100 + random.nextInt(200)) + "," + (100 + random.nextInt(200));
//			arg[28] = 70 + random.nextInt(49) + "";
            arg[20] = (600 + 1 + random.nextInt(50)) + "," + (800 + 1 + random.nextInt(50));
            arg[21] = (500 + 1 + random.nextInt(20)) + "," + (700 + 1 + random.nextInt(20));
            arg[22] = 3000 + 1 + random.nextInt(1000) + "";
            arg[27] = (300 + random.nextInt(150)) + "," + (150 + random.nextInt(150)) + "," + (150 + random.nextInt(150)) + "," + (100 + random.nextInt(200)) +
                    "," + (100 + random.nextInt(200)) + "," + (100 + random.nextInt(200));
            arg[28] = (80 + random.nextInt(10)) +"";

            // suffix = "^^568,123,192,209,172,221^75^^^^0"; 前6位是 焦点落到输入框及6位验证码之前输入时间间隔

        } else if (index == 106) {
            arg[0] = "E0106";
            arg[19] = rdoRequest.getPolicyTime() + ",";
            arg[20] = (random.nextInt(200) + 440) + "," + (random.nextInt(150) + 250);
        }else if (index == 111) {

            int randomValue = 0;
            int picCode = randomValue + RandomUtils.nextInt(1, 4);
            String field2 = "";
            String field3 = "";
            String field4 = "";
            String suffix = "";
            int startY = RandomUtils.nextInt(25, 35);
            int startX = RandomUtils.nextInt(50, 60);
            field2 = startX + "," + startY;
            field3 = field2;
            field4 = 600 + RandomUtils.nextInt(100, 500) + "";
            StringBuilder interval = new StringBuilder();
            interval.append(RandomUtils.nextInt(80, 90)).append(",");
            int times = RandomUtils.nextInt(50, 70);

            int picX = 312 + picCode * 2;
            StringBuilder x = new StringBuilder();
            StringBuilder y = new StringBuilder();
            int currentX = startX;
            int currentY = startY;
            for (int i = 0; i < times; i++) {
                interval.append(RandomUtils.nextInt(13, 30)).append(",");
                if (i > 4) {
                    currentY = currentY + RandomUtils.nextInt(0, 8) - 4;
                }
                y.append(currentY).append(",");
                if (i + 1 < times) {
                    currentX = RandomUtils.nextInt(currentX, (picX + currentX) / 2) / 2 * 2;
                    x.append(currentX).append(",");
                } else {
                    x.append(picX).append(",");
                }
            }
            //suffix = "^^^" + (Integer.valueOf(field4) - RandomUtils.nextInt(1, 15)) + "^" + StringUtils.removeEnd(y.toString(), ",") + "^" +
            //StringUtils.removeEnd(x.toString(), ",") + "^" + StringUtils.removeEnd(interval.toString(), ",") + "^0";

            //System.out.println(suffix);





            arg[0] = "E0111";
            arg[19] = rdoRequest.getPolicyTime() + "," + arg[16];
            arg[20] = (470 + 1 + random.nextInt(130)) + "," + (410 + 1 + random.nextInt(15));
            arg[21] = arg[20];
            arg[22] = 2000 + 1 + random.nextInt(1000) + "";

            arg[28] = arg[22];

            arg[29] = StringUtils.removeEnd(y.toString(), ",");
            arg[30] = StringUtils.removeEnd(x.toString(), ",");
            arg[31] = StringUtils.removeEnd(interval.toString(), ",");

        }else{
            arg[0] = "E0110";
            arg[19] = rdoRequest.getPolicyTime() + "," + arg[16];
            arg[20] = (170 + 1 + random.nextInt(20)) + "," + (350 + 1 + random.nextInt(20));
            arg[21] = (140 + 1 + random.nextInt(20)) + "," + (260 + 1 + random.nextInt(20));
            arg[22] = 2000 + 1 + random.nextInt(1000) + "";

            arg[27] = (150 + random.nextInt(150)) + "," + (150 + random.nextInt(150)) + "," + (150+ random.nextInt(150)) + "," + (100+ random.nextInt(200)) +
                    "," + (100+ random.nextInt(200)) + "," + (100+ random.nextInt(200));
            arg[28] = (100 + random.nextInt(49))  +"";
        }*/
        String method = (Long.valueOf(arg[16].substring(arg[16].length() - 3, arg[16].length()))) + "";
        //arg[3] = method;
        System.out.println("method " + method);
        String raw = HMACSHA1.combine(Integer.parseInt(method), arg[6], arg[5], arg[16], arg[19], arg[20], arg[21],
                arg[22], "0789d61770844d658227d619e8d459a0", "e95b4b9026e649398e2ff0f6e8711487", arg[1]);
        System.out.println("raw " + raw);
        arg[24] = HMACSHA1.B2H(HMACSHA1.HmacSHA1Encrypt(raw, "e95b4b9026e649398e2ff0f6e8711487" + arg[16])).toLowerCase();// sign
        String tmp = "";
        for (int j = 0; j < arg.length; j++) {
            tmp += arg[j];
            if (j == arg.length - 1) {
                break;
            }
            tmp += "^";
        }
        return tmp;
    }

}
