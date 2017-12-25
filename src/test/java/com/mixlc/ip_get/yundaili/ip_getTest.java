/**
 * Copyright (C), 2017-2017, lc
 * FileName: ip_getTest
 * Author:   mixlc
 * Date:     2017/12/25 0025 15:00
 * Description: 测试ipget
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.yundaili;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试ipget〉
 *
 * @author mixlc
 * @create 2017/12/25 0025
 * @since 1.0.0
 */
public class ip_getTest {
    @Test
    public void getip(){
        for(int i=1;i<=10;i++){
            String url = "http://www.ip3366.net/free/?stype=1&page="+i;
            ip_get ipGet = new ip_get();
            ipGet.getDomByUrl(url);
        }
    }
    @Test
    public void getHead(){
        String url = "http://www.ip3366.net/free/?stype=1&page=i";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements thead = doc.getElementById("list").child(0).getElementsByTag("thead").select("tr").select("th");
            System.out.println(thead);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
