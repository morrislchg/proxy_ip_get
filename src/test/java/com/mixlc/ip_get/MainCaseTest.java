/**
 * Copyright (C), 2017-2017, lc
 * FileName: MainCaseTest
 * Author:   mixlc
 * Date:     2017/12/27 0027 16:54
 * Description: 小测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get;

import com.mixlc.ip_get.yundaili.ip_get;
import com.mixlc.ip_get.zhandaye.IpGet;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈小测试〉
 *
 * @author mixlc
 * @create 2017/12/27 0027
 * @since 1.0.0
 */
public class MainCaseTest {
    @Test
    public void testname(){
        String url = "http://ip.zdaye.com/img/m_0235a1f6184ffe12.gif?2017/12/27%2016:28:56";
        System.out.println(url.substring(url.indexOf("m_"),url.indexOf("?")));;
    }
    @Test
    public void testPath(){
        File baseDir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue\\");
        for(File file:baseDir.listFiles()){
            String name = file.getParentFile().getPath();
            System.out.println(name);
            Assert.assertTrue("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue".equals(name));
        }
    }
    @Test
    public void getIp(){
        zhandaye();
        yundaili();
        xcdl();
        kuaidaili();
    }
    public void zhandaye(){
        String url = "http://ip.zdaye.com/";
        IpGet ipGet = new IpGet(url);
        ipGet.getIps();
    }
    public void yundaili(){
        for(int i=1;i<=10;i++){
            String url = "http://www.ip3366.net/free/?stype=1&page="+i;
            ip_get ipGet = new ip_get();
            ipGet.getDomByUrl(url);
        }
    }
    public void xcdl(){
        for(int i=1;i<6;i++){
            com.mixlc.ip_get.xicidaili.IpGet ipGet = new com.mixlc.ip_get.xicidaili.IpGet();
            String url = "http://www.xicidaili.com/nn/"+i;
            ipGet.getDomByUrl(url);
        }
    }
    public void kuaidaili(){
        for(int i=1;i<=20;i++){
            String url = "http://www.kuaidaili.com/free/inha/"+i+"/";
            com.mixlc.ip_get.kuaidaili.IpGet ipGet = new com.mixlc.ip_get.kuaidaili.IpGet();
            ipGet.getDomByUrl(url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
