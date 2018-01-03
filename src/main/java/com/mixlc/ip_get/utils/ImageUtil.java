/**
 * Copyright (C), 2017-2017, lc
 * FileName: ImageUtil
 * Author:   mixlc
 * Date:     2017/12/29 0029 17:11
 * Description: gif转jpg
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import com.gif4j.GifFrame;
import com.gif4j.GifImage;
import com.gif4j.GifDecoder;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈gif转jpg〉
 *
 * @author mixlc
 * @create 2017/12/29 0029
 * @since 1.0.0
 */
public class ImageUtil {
    public static void main(String[] args) {
        final String source="C:\\Users\\Administrator\\Desktop\\testimg\\m_00cdadd4d169acfb.gif";

        ExecutorService pool= Executors.newFixedThreadPool(50);
        //测试多线程并发
        for (int i = 0; i < 1; i++) {
            final int k=i;
            final String dest="C:\\Users\\Administrator\\Desktop\\testimg\\"+(i+1)+".jpg";
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    gif4jToJpg(source,dest);
                //    gifToJpg(source,dest,100);
                    System.out.println(Thread.currentThread().getName()+" is create jpp "+k);
                }
            });
        }
        pool.shutdown();
    }

    /**
     * gif图转jpg图片(使用jimi)
     *
     * @param source
     *            gif图片路径
     * @param dest
     *            目标图片路径
     * @param quality
     *            图片质量
     */
//    public static boolean gifToJpg(String source, String dest, int quality) {
//        if (dest == null || dest.trim().equals(""))
//            dest = source;
//
//        if (!dest.toLowerCase().trim().endsWith("jpg")) {
//            dest += ".jpg";
//        }
//
//        if (quality < 0 || quality > 100 || (quality + "") == null || (quality + "").equals("")) {
//            quality = 75;
//        }
//
//        try {
//            JPGOptions options = new JPGOptions();
//            options.setQuality(quality);
//            ImageProducer image = Jimi.getImageProducer(source);
//            JimiWriter writer = Jimi.createJimiWriter(dest);
//            writer.setSource(image);
//            writer.setOptions(options);
//            writer.putImage(dest);
//        } catch (JimiException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    /**
     * gif图转jpg图片(使用gif4j)
     *
     * @param source
     *            gif图片路径
     * @param dest
     *            目标图片路径
     */
    public static boolean gif4jToJpg(String source, String dest) {
        GifImage image= null;
        try {
            image = GifDecoder.decode(new File(source));
            if (image.getNumberOfFrames()>0){
                GifFrame frame=image.getFrame(0);
                BufferedImage bufferedImage=frame.getAsBufferedImage();
                OutputStream out = new FileOutputStream(dest);
                ImageIO.write(bufferedImage, "jpeg", out);//将frame 按jpeg格式  写入out中
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * gif4j获取图片信息 参考http://www.gif4j.com/java-gif4j-pro-gif-image-load-decode.htm#loadgifimageextractmetainfo
     * * @param file
     * @return
     * @throws IOException
     */
    public static GifImage loadGifImageAndExtractMetaInfo(File file) throws IOException {
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
        return gifImage;
    }
}
