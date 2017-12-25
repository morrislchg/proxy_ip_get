/**
 * Copyright (C), 2017-2017, lc
 * FileName: ip_get
 * Author:   mixlc
 * Date:     2017/12/25 0025 14:42
 * Description: 云代理获取代理ip
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.yundaili;

import com.mixlc.ip_get.mysql.MysqlDriver;
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
 * 〈云代理获取代理ip〉
 *
 * @author mixlc
 * @create 2017/12/25 0025
 * @since 1.0.0
 */
public class ip_get {
    public final static Map<String,String> keyMap = KeyMap.getKeyMap();
    private List<String> keylist = new ArrayList<String>();
    public void getDomByUrl(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementById("list").child(0).getElementsByTag("tbody").eq(0).select("tr");
            Elements thead = doc.getElementById("list").child(0).getElementsByTag("thead").select("tr").select("th");
            getHead(thead);
            getRow(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getHead(Elements elements){
        Iterator it = elements.iterator();
        while(it.hasNext()){
            Element element = (Element) it.next();
            keylist.add(element.text());
        }
    }
    private void getRow(Elements elements){
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

    }
    public Map<String,String> getColumn(Elements cols){
        Iterator iterator = cols.iterator();
        Map<String,String> row = new HashMap<String, String>();
        int i=0;
        while (iterator.hasNext()){
            Element element1 = (Element) iterator.next();
            String key = getKeyByDataTitle(keylist.get(i));
            String value = element1.text();
            row.put(key,value);
            i++;
        }
        return row;
    }
    public String getKeyByDataTitle(String data_title){
        return keyMap.get(data_title);
    }
}
