/**
 * Copyright (C), 2017-2017, lc
 * FileName: SqlFactory
 * Author:   mixlc
 * Date:     2017/12/25 0025 10:20
 * Description: 产生sql
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.kuaidaili;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈产生sql〉
 *
 * @author mixlc
 * @create 2017/12/25 0025
 * @since 1.0.0
 */
public class SqlFactory {
    private Map<String, String> map = null;
    private List<Map<String, String>> maplist = null;
    private StringBuffer sqlBuffer = new StringBuffer();
    private StringBuffer keyBuffer = new StringBuffer();
    private StringBuffer valueBuffer = new StringBuffer();

    public SqlFactory(Map<String, String> map) {
        this.map = map;
    }

    public SqlFactory(List<Map<String, String>> maplist) {
        this.maplist = maplist;
    }
    public String getSql(){
        if(map==null){
            return getSqlListMap();
        }else{
            return getSqlByMap();
        }
    }
    public String getSqlByMap() {
        sqlBuffer = new StringBuffer("INSERT INTO ip_address (");
        getKeyValueBuffer();
        sqlBuffer.append(keyBuffer).append(") values(").append(valueBuffer).append("on duplicate key update ip = values(ip)");
        return sqlBuffer.toString();
    }

    public String getSqlListMap() {
        sqlBuffer = new StringBuffer("INSERT INTO ip_address ");
        getSqlBuffer();
        sqlBuffer.append(keyBuffer).append(" values").append(valueBuffer).append("on duplicate key update ip = values(ip)");
        return sqlBuffer.toString();
    }

    public void getKeyValueBuffer() {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (keyBuffer.length() != 0) {
                keyBuffer.append(",");
                valueBuffer.append(",");
            }
            keyBuffer.append(entry.getKey());
            valueBuffer.append(entry.getValue());
        }
    }

    public void getSqlBuffer() {
        keyBuffer.append("(");
        Map<String,String> keymap = maplist.get(0);
        Iterator<String> iterator = keymap.keySet().iterator();
        List<String> keylist = new ArrayList<String>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (keyBuffer.length() != 1) {
                keyBuffer.append(",");
            }
            keyBuffer.append(key);
            keylist.add(key);
        }
        keyBuffer.append(")");
        getValueBufferByKeyList(keylist);
    }

    public void getValueBufferByKeyList(List<String> keylist) {
        int j = 0;
        for (Map<String, String> entrymap : maplist) {
            if (j != 0) {
                valueBuffer.append(",");
            }
            valueBuffer.append("(");
            int i = 0;
            for (String key : keylist) {
                if (i != 0) {
                    valueBuffer.append(",");
                }
                valueBuffer.append("'");
                valueBuffer.append(entrymap.get(key));
                valueBuffer.append("'");
                i++;
            }
            valueBuffer.append(")");
            j++;
        }
    }
}
