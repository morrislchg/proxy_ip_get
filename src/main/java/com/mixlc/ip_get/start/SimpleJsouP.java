/**
 * Copyright (C), 2017-2017, lc
 * FileName: SimpleJsouP
 * Author:   Administrator
 * Date:     2017/12/23 0023 14:41
 * Description: 开始
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.start;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈开始〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class SimpleJsouP {
    public void get_usr(String url){
        try {
            Document doc = Jsoup.connect(url)
                    .get();
            System.out.println(doc);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
