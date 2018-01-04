/**
 * Copyright (C), 2017-2018, lc
 * FileName: GifTest1
 * Author:   mixlc
 * Date:     2018/1/2 0002 15:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.gif;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifFrame;
import com.gif4j.GifImage;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/2 0002
 * @since 1.0.0
 */
public class GifTest1 {
    @Test
    public void gif4jTest(){
    //    String gifName = "m_00cdadd4d169acfb.gif";
        String gifName = "test.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\testimg\\";
        File file = new File(path+gifName);
        File dest = new File(path+"0.gif");
        GifImage srcImage = null;
        try {
            srcImage = GifDecoder.decode(file);
            GifEncoder.encode(srcImage,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } @Test
    public void gif4jTest1(){
       String gifName = "m_0aeab1984a7ec77e.gif";
      //  String gifName = "test.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue\\";
        File file = new File(path+gifName);

        GifImage srcImage = null;
        try {
            srcImage = GifDecoder.decode(file);
            int num = srcImage.getNumberOfFrames();
            if (num>0){
                for(int i=0;i<num;i++){
                    GifFrame frame=srcImage.getFrame(i);
                    BufferedImage bufferedImage=frame.getAsBufferedImage();
                    File dest = new File(path+i+".jpg");
                    OutputStream out = new FileOutputStream(dest);
                    ImageIO.write(bufferedImage, "jpeg", out);//将frame 按jpeg格式  写入out中
                    out.flush();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testInformation() throws IOException {
        String gifName = "m_5ae599b1b091201c.gif";
        //  String gifName = "test.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\testimg\\";
        File file = new File(path+gifName);
        // load and decode gif image from the file
        GifImage gifImage = GifDecoder.decode(file);
        // print general GIF image info
        System.out.println("gif image format version: " + gifImage.getVersion());
        System.out.println("gif image logic screen width: " + gifImage.getScreenWidth());
        System.out.println("gif image logic screen height: " + gifImage.getScreenHeight());
        // check if one or more comments present
        if (gifImage.getNumberOfComments() > 0) {
            // get iterator over gif image textual comments
            Iterator commentsIterator = gifImage.comments();
            while (commentsIterator.hasNext())
                System.out.println(commentsIterator.next()); // print comments
        }
        System.out.println("number of frames: " + gifImage.getNumberOfFrames());
        // below we iterate frames in loop
        // but it can also be done using Iterator instance: gifImage.frames()
        for (int i = 0; i < gifImage.getNumberOfFrames(); i++) {
            System.out.println("------frame(" + (i + 1) + ")---------");
            GifFrame frame = gifImage.getFrame(i);
            System.out.println("width: " + frame.getWidth());
            System.out.println("height: " + frame.getHeight());
            System.out.println("position: " + frame.getX() + "," + frame.getY());
            System.out.println("disposal method: " + frame.getDisposalMethod());
            System.out.println("delay time: " + frame.getDelay());
            System.out.println("is interlaced: " + frame.isInterlaced());
            // get frame's color model
            IndexColorModel frameColorModel = frame.getColorModel();
            System.out.println("number of colors: " + frameColorModel.getMapSize());
            System.out.println("is transparent: " + frameColorModel.hasAlpha());
            System.out.println("transparent index: " + frameColorModel.getTransparentPixel());
            //get frame's representation as an Image
            Image image = frame.getAsImage();
            //get frame's representation as a BufferedImage
            BufferedImage bufferedImage = frame.getAsBufferedImage();

        }
    }
    @Test
    public void gifToJpg() throws Exception
    {
        String gifName = "m_5ae599b1b091201c.gif";
        //  String gifName = "test.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\testimg\\";
        File infile = new File(path+gifName);
        BufferedImage src = javax.imageio.ImageIO.read(infile);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);

        BufferedImage tag = new BufferedImage(wideth , height ,BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src, 0, 0, wideth , height , null);
        FileOutputStream out = new FileOutputStream(path+"0.jpg");
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        out.close();
    }
}
