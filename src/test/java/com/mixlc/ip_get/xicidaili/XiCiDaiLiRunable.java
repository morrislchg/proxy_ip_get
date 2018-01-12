/**
 * Copyright (C), 2017-2018, lc
 * FileName: XiCiDaiLiRunable
 * Author:   mixlc
 * Date:     2018/1/12 0012 17:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.xicidaili;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class XiCiDaiLiRunable implements Runnable{
    @Override
    public void run(){
        for(int i=1;i<6;i++){
            IpGet ipGet = new IpGet();
            String url = "http://www.xicidaili.com/nn/"+i;
            ipGet.getDomByUrl(url);
        }
    }
}
