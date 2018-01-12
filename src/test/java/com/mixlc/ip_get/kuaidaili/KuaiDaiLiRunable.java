/**
 * Copyright (C), 2017-2018, lc
 * FileName: KuaiDaiLiRunable
 * Author:   mixlc
 * Date:     2018/1/12 0012 17:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.kuaidaili;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class KuaiDaiLiRunable implements Runnable{
    @Override
    public void run() {
        for(int i=1;i<=20;i++){
            String url = "http://www.kuaidaili.com/free/inha/"+i+"/";
            IpGet ipGet = new IpGet();
            ipGet.getDomByUrl(url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
