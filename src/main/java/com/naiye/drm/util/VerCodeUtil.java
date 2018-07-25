package com.naiye.drm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class VerCodeUtil {
    public static final String url = "http://api.ysdm.net/create.xml";

    public static String getParam(int typeid) {

        return "username=wjg121&password=wjg121&timeout=94000&softid=71939&softkey=3d2ee08c3c1343aca956fb3c728ca842&typeid=" + typeid;
    }

    private static final Logger logger = LoggerFactory
            .getLogger(VerCodeUtil.class);


    public static String getVC(String url, String param,
                               byte[] data) {
        String result = "";
        try {
            long time = (new Date()).getTime();
            URL u = null;
            HttpURLConnection con = null;
            String boundary = "----------" + Tools.getUUID();
            String boundarybytesString = "\r\n--" + boundary + "\r\n";
            OutputStream out = null;

            u = new URL(url);

            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(95000); //此值与timeout参数相关，如果timeout参数是90秒，这里就是95000，建议多5秒
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(true);
            con.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            out = con.getOutputStream();

            for (String paramValue : param.split("[&]")) {
                out.write(boundarybytesString.getBytes("UTF-8"));
                String paramString = "Content-Disposition: form-data; name=\""
                        + paramValue.split("[=]")[0] + "\"\r\n\r\n" + paramValue.split("[=]")[1];
                out.write(paramString.getBytes("UTF-8"));
            }
            out.write(boundarybytesString.getBytes("UTF-8"));

            String paramString = "Content-Disposition: form-data; name=\"image\"; filename=\""
                    + "sample.gif" + "\"\r\nContent-Type: image/gif\r\n\r\n";
            out.write(paramString.getBytes("UTF-8"));

            out.write(data);

            String tailer = "\r\n--" + boundary + "--\r\n";
            out.write(tailer.getBytes("UTF-8"));

            out.flush();
            out.close();

            StringBuffer buffer = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(buffer.toString())));
            Element root = doc.getDocumentElement();
            NodeList books = root.getChildNodes();
            if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    if (book.getNodeName().equals("Result")) {
                        result = book.getFirstChild().getNodeValue();
                        break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("得到图形验证码：" + result);
        return result;

    }


    public static void main(String[] args) {
//        String piccode = VerCodeUtil.getVC(VerCodeUtil.url, VerCodeUtil.getParam(3040), tuyandata);
    }
}
