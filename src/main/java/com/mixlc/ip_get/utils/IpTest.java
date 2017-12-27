/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpTest
 * Author:   mixlc
 * Date:     2017/12/26 0026 16:20
 * Description: 测试ip是否可用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import sun.misc.IOUtils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试ip是否可用〉
 *
 * @author mixlc
 * @create 2017/12/26 0026
 * @since 1.0.0
 */
public class IpTest {
    public static boolean testIp(String ip,String port){
        boolean result = false;
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr=null;
            addr=new InetSocketAddress(ip,Integer.valueOf(port));
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);
            conn.setConnectTimeout(500);
            System.out.println(conn.toString());
            InputStream in = conn.getInputStream();
            String s = org.apache.commons.io.IOUtils.toString(in);
            System.out.println(s);
            result = true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
