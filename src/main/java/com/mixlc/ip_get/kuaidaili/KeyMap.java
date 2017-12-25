/**
 * Copyright (C), 2017-2017, lc
 * FileName: KeyMap
 * Author:   mixlc
 * Date:     2017/12/25 0025 8:44
 * Description: 键值对
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.kuaidaili;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈键值对〉
 *
 * @author mixlc
 * @create 2017/12/25 0025
 * @since 1.0.0
 */
public class KeyMap {
    public static Map<String,String> getKeyMap(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("IP","ip");
        map.put("PORT","port");
        map.put("匿名度","anonymity");
        map.put("类型","type");
        map.put("位置","location");
        map.put("响应速度","speed");
        map.put("最后验证时间","validate_time");
        return map;
    }
}
