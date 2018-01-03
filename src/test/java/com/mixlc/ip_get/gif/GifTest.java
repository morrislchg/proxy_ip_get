/**
 * Copyright (C), 2017-2017, lc
 * FileName: GifTest
 * Author:   mixlc
 * Date:     2017/12/29 0029 12:53
 * Description: gif测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.gif;

import com.gif4j.GifImage;
import com.mixlc.ip_get.utils.GifDecoder;
import com.mixlc.ip_get.utils.ImageUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈gif测试类〉
 *
 * @author mixlc
 * @create 2017/12/29 0029
 * @since 1.0.0
 */
public class GifTest {
    @Test
    public void testGif(){
        //String gifName = "m_00cdadd4d169acfb.gif";
        String gifName = "m_29ae92f9ed4eb8ee.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\";
        try {
            GifDecoder decoder = new GifDecoder();
            decoder.read(path+gifName);
            int n = decoder.getFrameCount(); //得到frame的个数
            String[] subPic = new String[n];
            for (int i = 0; i < n; i++) {
                BufferedImage frame = decoder.getFrame(i); //得到帧
                //int delay = decoder.getDelay(i); //得到延迟时间
                //生成小的JPG文件
                subPic[i] = path + String.valueOf(i)+ ".jpg";
                FileOutputStream out = new FileOutputStream(subPic[i]);
                ImageIO.write(frame, "jpeg", out);
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//                encoder.encode(frame); //存盘
                out.flush();
                out.close();
            }
           System.out.println(subPic);
        } catch (Exception e) {
            System.out.println( "splitGif Failed!");
            e.printStackTrace();
            System.out.println("----");
        }
    }



    @Test
    public void tetsJpeg(){
        //得到图片缓冲区
        BufferedImage bi=new BufferedImage(150,70, BufferedImage.TYPE_INT_RGB);
        //得到画笔
        Graphics2D g2=(Graphics2D) bi.getGraphics();
        //填充背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 150, 70);
        //设置边框
        g2.setColor(Color.RED);
        g2.drawRect(0, 0, 149, 69);
        //向图片上写字符串
        g2.setFont(new Font("宋体", Font.BOLD, 10));
        g2.setColor(Color.BLACK);
        g2.drawString("grup", 19, 20);
        try {
            ImageIO.write(bi, "GIF", new FileOutputStream("C:\\Users\\Administrator\\Desktop\\testimg\\g.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ocrTest() throws IOException {
        Map<BufferedImage,String> trainMap = null;
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            File dir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\新建文件夹\\");
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        System.out.println(trainMap);
    }
}
