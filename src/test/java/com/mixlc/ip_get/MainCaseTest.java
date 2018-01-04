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
}
