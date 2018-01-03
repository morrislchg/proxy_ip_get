/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGet
 * Author:   mixlc
 * Date:     2017/12/27 0027 10:33
 * Description: 战大爷ip获取
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.mysql.MysqlDriver;
import com.mixlc.ip_get.utils.SqlFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈战大爷ip获取〉
 *
 * @author mixlc
 * @create 2017/12/27 0027
 * @since 1.0.0
 */
public class IpGet {
    private String url;
    private String  cookies;
    public IpGet(String url) {
        this.url = url;
    }

    public void getIps(){
       Elements elements = getDomByUrl();
        getRow(elements);
    }

    public Elements getDomByUrl(){
        try {
            Connection connection = Jsoup.connect(url);
            Connection.Response response = connection.execute();
            Map<String,String>  ocookies =  response.cookies();
            setCookie(ocookies);
            Document doucment = response.parse();
            Elements elements = doucment.getElementById("ipc").getElementsByTag("tbody").select("tr");
            return elements;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setCookie(Map<String,String> ocookies){
        String Cookie = ocookies.toString();
        Cookie = Cookie.substring(Cookie.indexOf("{")+1, Cookie.lastIndexOf("}"));
        this.cookies = Cookie.replaceAll(",", ";");
    }
    public void getRow(Elements elements){
        Iterator it = elements.iterator();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        int i=0;
        while (it.hasNext()){
            if(i==0){
                i++;
                it.next();
                continue;
            }
            Element element = (Element) it.next();
            Elements cols = element.select("td");
            Map<String,String> row = getColumn(cols);
      //      list.add(row);
            i++;
        }
       // saveRows(list);
    }
    public void saveRows(List<Map<String,String>> list){
        SqlFactory sqlFactory = new SqlFactory(list);
        String sql = sqlFactory.getSql();
        MysqlDriver mysqlDriver = new MysqlDriver();
        mysqlDriver.sqlEecute(sql);
    }
    public Map<String,String> getColumn(Elements elements){
        Iterator iterator = elements.iterator();
        Map<String,String> row = new HashMap<String, String>();
        int i=0;
        while (iterator.hasNext()){
            Element element1 = (Element) iterator.next();
            if(i==1){
                String key = "ip";
                String value = element1.text();
                row.put(key,value);
            }else if(i==2){
                String key = "port";
                String value = element1.getElementsByTag("img").eq(0).attr("abs:src");
//                System.out.println("http://ip.zdaye.com/"+value);
                try {
                    downloadImages(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }


              //  row.put(key,value);
            }else{
                i++;
                continue;
            }
            i++;
        }
        return row;
    }
    public void downloadImages(String url){
        url = url.replaceAll("\\\\n","");
        System.out.println(url);
        Connection conn = Jsoup.connect(url);
        conn.header("Host","ip.zdaye.com");
        conn.header("Connection","keep-alive");
        conn.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        conn.header("Accept","*/*");
        conn.header("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
        conn.header("Cache-Control","max-age=0");
        conn.header("Accept-Encoding","gzip, deflate");
        conn.header("Host","ip.zdaye.com");
        conn.header("Referer","http://ip.zdaye.com/");
        conn.header("Cookie",this.cookies);
        conn.followRedirects(false);
        conn.ignoreContentType(true);
        Connection.Response response = null;
        try {
            response = conn.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] img = response.bodyAsBytes();
        byteToImage(img,"C:\\Users\\Administrator\\Desktop\\imgs\\"+getNameByPath(url));
        System.out.println(img.length);
    }
    public void byteToImage(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }
    public String getNameByPath(String path){
        return path.substring(path.indexOf("m_"),path.indexOf("?"));
    }
}
