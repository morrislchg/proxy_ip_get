/**
 * Copyright (C), 2017-2017, lc
 * FileName: MysqlDriver
 * Author:   mixlc
 * Date:     2017/12/23 0023 16:22
 * Description: 数据库操作
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.mysql;
import java.sql.*;
import java.util.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈数据库操作〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class MysqlDriver {
    private final static String url = "jdbc:mysql://localhost:3306/ip_get?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String user = "root";
    private final static String password = "root";
    private final static String Driver = "com.mysql.cj.jdbc.Driver";
    public Statement getStatement(){
        Statement statement = null;
        try{
            Class.forName(Driver);
            Connection connection = DriverManager.getConnection(url,user,password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
    public void sqlEecute(String sql){
        Statement statement = getStatement();
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(sql);
        }
    }
    public List<Map<String,String>> executeQuery(String sql){
        Statement statement = getStatement();
        ResultSet resultSet = null;
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        try {
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData= resultSet.getMetaData();
            int col = metaData.getColumnCount();
            while (resultSet.next()){
                Map<String,String> map = new HashMap<String, String>();
                for(int i=1;i<=col;i++){
                    map.put(metaData.getColumnName(i),resultSet.getString(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
