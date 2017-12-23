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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取ip〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class IpGet {
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
        while (it.hasNext()){
            Element element = (Element) it.next();
            Elements cols = element.select("td");
            getColumn(cols);
        }
    }
    public void getColumn(Elements cols){
        Iterator iterator = cols.iterator();
        while (iterator.hasNext()){
            Element element1 = (Element) iterator.next();
        }
    }
}
