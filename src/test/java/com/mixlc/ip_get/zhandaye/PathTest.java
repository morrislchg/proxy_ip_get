/**
 * Copyright (C), 2017-2018, lc
 * FileName: PathTest
 * Author:   mixlc
 * Date:     2018/1/10 0010 9:22
 * Description: 路径策似
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.utils.BlackColor;
import com.mixlc.ip_get.utils.BlueCode;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈路径策似〉
 *
 * @author mixlc
 * @create 2018/1/10 0010
 * @since 1.0.0
 */
public class PathTest {
    @Test
    public void testPath(){
        File file = new File("C:\\Users\\Administrator\\AppData\\Local\\Temp\\1515470537.jpg");
        Assert.assertTrue("1515470537.jpg".equals(file.getName()));
    }@Test
    public void testPath1(){
        File file = new File("C:\\Users\\Administrator\\AppData\\Local\\Temp\\1515470537.jpg");
        Assert.assertTrue("C:\\Users\\Administrator\\AppData\\Local\\Temp".equals(file.getParentFile().getPath()));
    }
    @Test
    public void testFiveBlack() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\80_29\\m_2c0d99e6d00b6c11.gif");
        BlackColor blackColor = new BlackColor(file);
        System.out.println(blackColor.getCode());;
    }
    @Test
    public void testBlueClear() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\80_29\\m_2c0d99e6d00b6c11.gif");
        BlueCode blueCode = new BlueCode(file);
        System.out.println(blueCode.getCode());
    }
}
