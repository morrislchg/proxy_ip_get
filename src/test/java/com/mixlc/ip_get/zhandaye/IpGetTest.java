/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGetTest
 * Author:   mixlc
 * Date:     2017/12/27 0027 12:59
 * Description: 站大爷ip
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.utils.ImageUtil2;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import com.sun.imageio.plugins.gif.GIFImageWriter;
import com.sun.imageio.plugins.gif.GIFImageWriterSpi;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈站大爷ip〉
 *
 * @author mixlc
 * @create 2017/12/27 0027
 * @since 1.0.0
 */
public class IpGetTest {
    @Test
    public void ipgetTest(){
        String url = "http://ip.zdaye.com/";
        IpGet ipGet = new IpGet(url);
        ipGet.getIps();
    }
    @Test
    public void oragnizeImagesBySize(){
        File dir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\");
        File[] files = dir.listFiles();
        System.out.println(files.length);
        for(File file:files){
            try {
                if(file.isDirectory()){
                    continue;
                }
                int width = ImageIO.read(file).getWidth();
                int height = ImageIO.read(file).getHeight();
                String  path = "C:\\Users\\Administrator\\Desktop\\imgs\\"+width+"_"+height+"\\";
                System.out.println(path);
                File file1 = new File(path);
                if(!file1.exists()){
                    if(file1.mkdirs()){
                        System.out.println("创建目录成功");
                    }else {
                        System.out.println("创建目录失败");
                    }
                }
                File file2 = new File(path+"\\"+file.getName());
                System.out.println(path+"\\"+file.getName());
                if(file.renameTo(file2)){
                    System.out.println(file.getName()+"转移成功");
                }else{
                    System.out.println(file.getName()+"转移失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void readCode(){
        File dir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29\\");
        File[] files = dir.listFiles();
        for(File file:files){
            try {
            //   String text =  ImagePreProcess.getAllOcr(file);
              // System.out.println(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void spolitImage(){
        File dir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29\\");
        File[] files = dir.listFiles();
        File dir1 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\");
        if(!dir1.exists()){
            dir1.mkdirs();
        }
        System.out.println(files.length);
        for(File file:files){
            try {
               // BufferedImage bufferedImage = removeBackgroud(file.getPath());
                BufferedImage bufferedImage = ImageIO.read(new File(file.getPath()));
                System.out.println("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\"+file.getName());
                File file1 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\"+file.getName());
                ImageIO.write(bufferedImage,"jpg",file1);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            BufferedImage bufferedImage = null;
//            try {
//                bufferedImage = ImageIO.read(file);
//                splitImage(bufferedImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
    public BufferedImage removeBackgroud(String path)
            throws Exception {
        BufferedImage img = ImageIO.read(new File(path));
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
//                if (isBackGround(img.getRGB(x, y)) == 1) {
//                    img.setRGB(x, y, Color.WHITE.getRGB());
//                }
            }
        }
        return img;
    }
    public BufferedImage removeBlank(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        int start = 0;
        int end = 0;
        Label1: for (int y = 0; y < height; ++y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
                if (count >= 1) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
                if (count >= 1) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }

    public List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        List<Integer> weightlist = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            weightlist.add(count);
        }
        for (int i = 0; i < weightlist.size();) {
            int length = 0;
            while (weightlist.get(i++) > 1) {
                length++;
            }
            if (length > 12) {
                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,
                        length / 2, height)));
                subImgs.add(removeBlank(img.getSubimage(i - length / 2 - 1, 0,
                        length / 2, height)));
            } else if (length > 3) {
                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,
                        length, height)));
            }
        }
        return subImgs;
    }
    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }

    public static int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
            return 1;
        }
        return 0;
    }
    public static int isBackGround(int colorInt){
        Color color = new Color(colorInt);
        if(color.getRed()==228&&color.getGreen()==233&&color.getBlue()==238){
            return 1;
        }
        return 0;
    }
    @Test
    public void readgif1(){
        String src = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29\\m_00cdadd4d169acfb.gif";
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(src));
            Graphics2D g = bufferedImage.createGraphics();
            try {
                // Here's the trick, with DstOver we'll paint "behind" the original image
                g.setComposite(AlphaComposite.DstOver);
            //    g.setColor(Color.GRAY);
                g.fill(new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()));
            }
            finally {
                g.dispose();
            }

            File file1 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\1.gif");
            ImageIO.write(bufferedImage,"GIF",file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void redgif(){
//        String src = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29\\m_00cdadd4d169acfb.gif";
//        String src1 = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\";
        String src = "C:\\Users\\Administrator\\Desktop\\testimg\\test.gif";
        String src1 = "C:\\Users\\Administrator\\Desktop\\testimg\\";
        int frame = 0;
        FileImageInputStream in = null;
        FileImageOutputStream out = null;
        try {
            in = new FileImageInputStream(new File(src));
            ImageReaderSpi readerSpi = new GIFImageReaderSpi();
            GIFImageReader gifReader = (GIFImageReader) readerSpi.createReaderInstance();
            gifReader.setInput(in);
            int num = gifReader.getNumImages(true);
            System.out.println(num);
            // 要取的帧数要小于总帧数
            if (num > frame) {
                ImageWriterSpi writerSpi = new GIFImageWriterSpi();
                GIFImageWriter writer = (GIFImageWriter) writerSpi.createWriterInstance();
                for (int i = 0; i < num; i++) {
                 //   if (i == frame) {
                        File newfile = new File(src1+i+".gif");
                        out = new FileImageOutputStream(newfile);
                        writer.setOutput(out);
                     //    读取读取帧的图片
                        writer.write(gifReader.read(i));
                   // }
                }
            }
            out.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getType(){
        String src = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29\\m_00cdadd4d169acfb.gif";
    }
    @Test
    public void getGifCut(){
        try {
            // 起始坐标，剪切大小
            int x = 0;
            int y = 0;
            int width = 66;
            int height = 29;
            // 参考图像大小
            int clientWidth = 66;
            int clientHeight = 29;


            File file = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\m_00cdadd4d169acfb.gif");
            BufferedImage image = ImageIO.read(file);
            double destWidth = image.getWidth();
            double destHeight = image.getHeight();
            System.out.println("width:"+destWidth);
            System.out.println("height:"+destHeight);
            if(destWidth < width || destHeight < height)
                throw new Exception("源图大小小于截取图片大小!");

            double widthRatio = destWidth / clientWidth;
            double heightRatio = destHeight / clientHeight;

            x = Double.valueOf(x * widthRatio).intValue();
            y = Double.valueOf(y * heightRatio).intValue();
            width = Double.valueOf(width * widthRatio).intValue();
            height = Double.valueOf(height * heightRatio).intValue();

            System.out.println("裁剪大小  x:" + x + ",y:" + y + ",width:" + width + ",height:" + height);

            /************************ 基于三方包解决方案 *************************/
            String formatName = ImageUtil2.getImageFormatName(file);
            String pathSuffix = "." + formatName;
            String pathPrefix = ImageUtil2.getFilePrefixPath(file);
            String targetPath = pathPrefix  + System.currentTimeMillis() + pathSuffix;
            targetPath = ImageUtil2.cutImage(file.getPath(), targetPath, x , y , width, height);

            String bigTargetPath = pathPrefix  + System.currentTimeMillis() + pathSuffix;
            ImageUtil2.zoom(targetPath, bigTargetPath, 100, 100);

            String smallTargetPath = pathPrefix  + System.currentTimeMillis() + pathSuffix;
            ImageUtil2.zoom(targetPath, smallTargetPath, 50, 50);

            /************************ 基于JDK Image I/O 解决方案(JDK探索失败) *************************/
//              File destFile = new File(targetPath);
//              ImageCutterUtil.cutImage(file, destFile, x, y, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testByte(){
        byte b = (byte)234;
        System.out.println(b);
    }
    @Test
    public void testGif(){
        int width = 400;
        int height = 300;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D)image.getGraphics();
      //  image = g2d.getDeviceConfiguration().createCompatibleImage(width,height,Transparency.TRANSLUCENT);
        //g2d = image.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 400, 300);
      //  g2d.setStroke(new BasicStroke(1));
     //   g2d.dispose();
        try{
            ImageIO.write(image,"PNG",new File("C:\\Users\\Administrator\\Desktop\\imgs\\1.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void getResource(){

    }
}
