/**
 * Copyright (C), 2017-2018, lc
 * FileName: YunDailiRunable
 * Author:   mixlc
 * Date:     2018/1/12 0012 17:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.yundaili;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class YunDailiRunable implements Runnable{
    @Override
    public void run(){
        for(int i=1;i<=10;i++){
            String url = "http://www.ip3366.net/free/?stype=1&page="+i;
            ip_get ipGet = new ip_get();
            ipGet.getDomByUrl(url);
        }
    }
}
