/**
 * Copyright (C), 2017-2018, lc
 * FileName: SpotCodeTest
 * Author:   mixlc
 * Date:     2018/1/11 0011 11:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.utils.SpotCode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/11 0011
 * @since 1.0.0
 */
public class SpotCodeTest {
    @Test
    public void testSpot() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\m_02acc25575c23feb.gif");
        SpotCode spotCode = new SpotCode(file);
        spotCode.getCode();
    }
    @Test
    public void anlyzeName(){
        File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\m_0e88b1bab2bca190_8888.gif");
        String name = file.getName();
        String number = name.substring(name.lastIndexOf("_")+1,name.indexOf("."));
        System.out.println(number);
    }
    @Test
    public void getSinglecode() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\80_20");
        File[] list  = file.listFiles();
        for(File file1:list){
            SpotCode spotCode = new SpotCode(file1);
            spotCode.getCode();
        }
    }
}
