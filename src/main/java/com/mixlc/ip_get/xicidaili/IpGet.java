/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGet
 * Author:   mixlc
 * Date:     2017/12/26 0026 17:02
 * Description: 西刺
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.xicidaili;

import com.mixlc.ip_get.mysql.MysqlDriver;
import com.mixlc.ip_get.utils.IpTest;
import com.mixlc.ip_get.utils.KeyMap;
import com.mixlc.ip_get.utils.SqlFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈西刺〉
 *
 * @author mixlc
 * @create 2017/12/26 0026
 * @since 1.0.0
 */
public class IpGet {
    public final static Map<String,String> keyMap = KeyMap.getKeyMap();
    public void getDomByUrl(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementById("ip_list").getElementsByTag("tbody").eq(0).select("tr");
          //  System.out.println(elements);
            getRow(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println("xxxx");
            Element element = (Element) it.next();
            Elements cols = element.select("td");
            Map<String,String> row = getColumn(cols);
            System.out.println(row);
            list.add(row);
            i++;
        }
        SqlFactory sqlFactory = new SqlFactory(list);
        String sql = sqlFactory.getSql();
        MysqlDriver mysqlDriver = new MysqlDriver();
        mysqlDriver.sqlEecute(sql);

    }
    public Map<String,String> getColumn(Elements cols){
        Iterator iterator = cols.iterator();
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
                String value = element1.text();
                row.put(key,value);
            }else if(i==0){
                i++;
                continue;
            }else{
                break;
            }
            i++;
        }
        return row;
    }
}
