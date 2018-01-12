/**
 * Copyright (C), 2017-2018, lc
 * FileName: IpTestThread
 * Author:   mixlc
 * Date:     2018/1/12 0012 9:52
 * Description: ip有效性检测线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.mysql.MysqlDriver;
import com.mixlc.ip_get.utils.CheckIp;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈ip有效性检测线程〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class IpTestThread implements Runnable{
    private List<Map<String,String>> list;
    private int i=-1;
    private long seconds = 0;
    private Object obj = new Object();
    public IpTestThread(List<Map<String, String>> iplist) {
        seconds = System.currentTimeMillis();
        this.list = iplist;
    }

    @Override
    public void run(){
        testGet();
    }
    public void testGet(){
        while (i<list.size()){
            synchronized (obj){
                i++;
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println(i);
            Map<String,String> map = list.get(i);
            System.out.println(map);
            try{
                String mapresult = CheckIp.checkProxyIpOneByOne(map,"http://www.baidu.com");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(i==list.size()){
            long end = System.currentTimeMillis();
            double minutes = (double)(end-seconds)/1000.000;
            System.out.println(minutes+"秒");
        }
    }

    public static void main(String[] args) {
        String sql = "select t.ip,t.port from ip_address t";
        MysqlDriver mysqlDriver = new MysqlDriver();
        try {
            List<Map<String, String>> result = mysqlDriver.executeQuery(sql);
            IpTestThread ipTestThread = new IpTestThread(result);
            Thread thread = new Thread(ipTestThread,"检测窗口一");
            Thread thread2 = new Thread(ipTestThread,"检测窗口二");
            Thread thread3 = new Thread(ipTestThread,"检测窗口三");
            Thread thread4 = new Thread(ipTestThread,"检测窗口四");
            thread.start();
            thread2.start();
            thread3.start();
            thread4.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
