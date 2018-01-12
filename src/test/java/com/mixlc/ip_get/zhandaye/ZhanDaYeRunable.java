/**
 * Copyright (C), 2017-2018, lc
 * FileName: ZhanDaYeRunable
 * Author:   mixlc
 * Date:     2018/1/12 0012 17:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class ZhanDaYeRunable implements Runnable{
    @Override
    public void run(){
        String url = "http://ip.zdaye.com/";
        IpGet ipGet = new IpGet(url);
        ipGet.getIps();
    }
}
