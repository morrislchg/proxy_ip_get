/**
 * Copyright (C), 2017-2018, lc
 * FileName: IpTestThreadPool
 * Author:   mixlc
 * Date:     2018/1/12 0012 14:15
 * Description: 线程池来管理ip
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.mysql.MysqlDriver;
import com.mixlc.ip_get.utils.CheckIp;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈线程池来管理ip〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class IpTestThreadPool implements Callable<String> {
    Map<String,String> map = new HashMap<String,String>();

    public IpTestThreadPool(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String call(){
        String mapresult = "";
        try{
           mapresult = CheckIp.checkProxyIpOneByOne(map,"http://www.baidu.com");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapresult;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int taskSize = 40;
        long start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        String sql = "select t.ip,t.port from ip_address t";
        MysqlDriver mysqlDriver = new MysqlDriver();
        List<Map<String, String>> result = mysqlDriver.executeQuery(sql);
        for (int i = 0; i < result.size(); i++) {
            Callable c = new IpTestThreadPool(result.get(i));
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
//            System.out.println(">>>" + f.get().toString());
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();
        // 获取所有并发任务的运行结果
        int i=0;
        List<String> list1 = new ArrayList<String>();
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            String fr = (String) f.get();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(fr)){
                i++;
                list1.add(fr);
            }
        }
        long end = System.currentTimeMillis();
        double mis = (double)(end - start)/1000.000;
        System.out.println("用时======"+mis+"s");
        BigDecimal sbig = new BigDecimal(result.size());
        BigDecimal ubig = new BigDecimal(i);
        BigDecimal thund = new BigDecimal(100);
        if(i>0){
            ubig = ubig.divide(sbig,4,BigDecimal.ROUND_HALF_UP).multiply(thund);
        }
        System.out.println("共有ip:"+result.size()+"个   有效个数:"+i+"有效率为："+ubig.toString()+"%");
        for(String singleaddress:list1){
            String[] array = singleaddress.split(":");
            Connection connection = mysqlDriver.getConnection();
            CallableStatement callStmt = null;
            try {
                callStmt = connection.prepareCall("{call update_score(?)}");
                callStmt.setString(1,array[0]);
                callStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                if(callStmt!=null){
                    try {
                        callStmt.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if(connection!=null){
                    try {
                        connection.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }

        Connection connection = mysqlDriver.getConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = connection.prepareCall("{call minus_score()}");
            callStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            if(callStmt!=null){
                try {
                    callStmt.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}
