/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGet
 * Author:   mixlc
 * Date:     2017/12/23 0023 15:39
 * Description: 获取ip
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.kuaidaili;

import com.mixlc.ip_get.mysql.MysqlDriver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取ip〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class IpGet {
    public final static Map<String,String> keyMap = KeyMap.getKeyMap();
    public void getDomByUrl(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementById("list").child(0).getElementsByTag("tbody").eq(0).select("tr");
            getRow(elements);
            System.out.println(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getRow(Elements elements){
        Iterator it = elements.iterator();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while (it.hasNext()){
            Element element = (Element) it.next();
            Elements cols = element.select("td");
            Map<String,String> row = getColumn(cols);
            list.add(row);
        }
        SqlFactory sqlFactory = new SqlFactory(list);
        String sql = sqlFactory.getSql();
        MysqlDriver mysqlDriver = new MysqlDriver();
        mysqlDriver.sqlEecute(sql);
        System.out.println(Arrays.deepToString(list.toArray()));
    }
    public Map<String,String> getColumn(Elements cols){
        Iterator iterator = cols.iterator();
        Map<String,String> row = new HashMap<String, String>();
        while (iterator.hasNext()){
            Element element1 = (Element) iterator.next();
            String key = getKeyByDataTitle(element1.attr("data-title"));
            String value = element1.text();
            row.put(key,value);
        }
        return row;
    }
    public String getKeyByDataTitle(String data_title){
        return keyMap.get(data_title);
    }
}
