/**
 * Copyright (C), 2017-2018, lc
 * FileName: PathUtils
 * Author:   mixlc
 * Date:     2018/1/6 0006 9:06
 * Description: 类路径
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈类路径〉
 *
 * @author mixlc
 * @create 2018/1/6 0006
 * @since 1.0.0
 */
public class PathUtils {
    public static String getBasePath(){
        String basepath = BlueCode.class.getClassLoader().getResource("").getPath();
        return basepath;
    }
    public static String getBluePath(){
        return getBasePath() + "bluetrain\\";
    }
    public static String getBlackPath(){
        return getBasePath() + "blacktrain\\";
    }
}
