/**
 * Copyright (C), 2017-2018, lc
 * FileName: BlueReconize
 * Author:   mixlc
 * Date:     2018/1/3 0003 10:38
 * Description: 蓝色带干扰线
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.utils.BmpReader;
import com.mixlc.ip_get.utils.GifDecoder;
import com.mixlc.ip_get.utils.ImageProgress;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 〈一句话功能简述〉<br> 
 * 〈蓝色带干扰线〉
 *
 * @author mixlc
 * @create 2018/1/3 0003
 * @since 1.0.0
 */
public class BlueReconize {

    @Test
    public void testBlueReco() throws Exception {
//        File testDataDir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue");
//        final String destDir = testDataDir.getPath()+"\\temp";
//        for (File file : testDataDir.listFiles())
//        {
//            cleanLinesInImage(file, destDir);
////            cleanLinesInImage(file, destDir);
////            cleanLinesInImage(file, destDir);
//        }
        File baseDir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue\\pic\\");
        for(File file:baseDir.listFiles()){
            String filepath = file.getPath();
            String jpgpath = getJpgPathByFile(file)+getjpgNameByFileName(file.getName());
            BmpReader.bmpTojpg(filepath,jpgpath);
            cleanLinesInImage(new File(jpgpath), getJpgBlackPathByFile(file));
            BufferedImage bufferedImage = getBufferedImageByPath(getBackFilePathByFile(file));
            List<BufferedImage> list = ImageProgress.splitImage(bufferedImage);
            for(int i=0;i<list.size();i++){
                File file1 = new File(getSingleNumberPath(file,i));
                if(!file1.exists()){
                    file1.mkdirs();
                }
                ImageIO.write(list.get(i),"jpeg",file1);
            }
        }
    }
    public String getSingleNumberPath(File file,int i){
        Date date = new Date();
        String spname = String.valueOf(date.getTime());
        spname = spname +"_"+String.valueOf(i);
        return "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue\\single\\"+getjpgNameByFileName(file.getName()).charAt(i)+"\\"+spname+".jpg";
    }
    public String getjpgNameByFileName(String filename){
        String name = filename.substring(filename.lastIndexOf("_")+1,filename.lastIndexOf(".")) + ".jpg";
        return name;
    }
    public String getJpgPathByFile(File file){
        return file.getParentFile().getParentFile().getPath()+"\\jpgs\\";
    }
    public String getJpgBlackPathByFile(File file){
        return file.getParentFile().getParentFile().getPath()+"\\black\\";
    }
    public String getBackFilePathByFile(File file){
        return getJpgBlackPathByFile(file)+getjpgNameByFileName(file.getName());
    }
    public BufferedImage getBufferedImageByPath(String path) throws IOException {
        File file  = new File(path);
        BufferedImage bufferedImage = ImageIO.read(file);
        return bufferedImage;
    }
    /**
     *
     * @param sfile
     *            需要去噪的图像
     * @param destDir
     *            去噪后的图像保存地址
     * @throws IOException
     */
    public static void cleanLinesInImage(File sfile, String destDir)  throws IOException {
        File destF = new File(destDir);
        if (!destF.exists())
        {
            destF.mkdirs();
        }

        BufferedImage bufferedImage = ImageIO.read(sfile);
        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();

        // 灰度化
        int[][] gray = new int[w][h];
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                int argb = bufferedImage.getRGB(x, y);
                // 图像加亮（调整亮度识别率非常高）
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
                if (r >= 255)
                {
                    r = 255;
                }
                if (g >= 255)
                {
                    g = 255;
                }
                if (b >= 255)
                {
                    b = 255;
                }
                gray[x][y] = (int) Math
                        .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)
                                * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
            }
        }

        // 二值化
        int threshold = ostu(gray, w, h);
        BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                if (gray[x][y] > threshold)
                {
                    gray[x][y] |= 0x00FFFF;
                } else
                {
                    gray[x][y] &= 0xFF0000;
                }
                binaryBufferedImage.setRGB(x, y, gray[x][y]);
            }
        }
//        ImageIO.write(binaryBufferedImage, "jpg", new File(destDir, sfile
//                .getName()));
        //去除干扰线条
        for(int y = 1; y < h-1; y++){
            for(int x = 1; x < w-1; x++){
                boolean flag = false ;
                if(isBlack(binaryBufferedImage.getRGB(x, y))){
                    //左右均为空时，去掉此点
//                    if(isWhite(binaryBufferedImage.getRGB(x-1, y)) && isWhite(binaryBufferedImage.getRGB(x+1, y))){
//                        flag = true;
//                    }
                    //上下均为空时，去掉此点
                    if(isWhite(binaryBufferedImage.getRGB(x, y+1)) && isWhite(binaryBufferedImage.getRGB(x, y-1))){
                        flag = true;
                    }
                    //斜上下为空时，去掉此点
//                    if(isWhite(binaryBufferedImage.getRGB(x-1, y+1)) && isWhite(binaryBufferedImage.getRGB(x+1, y-1))){
//                        flag = true;
//                    }
//                    if(isWhite(binaryBufferedImage.getRGB(x+1, y+1)) && isWhite(binaryBufferedImage.getRGB(x-1, y-1))){
//                        flag = true;
//                    }
                    if(flag){
                        binaryBufferedImage.setRGB(x,y,-1);
                    }
                }
            }
        }


        // 矩阵打印
        for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                if (isBlack(binaryBufferedImage.getRGB(x, y)))
                {
                    System.out.print("*");
                } else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        ImageIO.write(binaryBufferedImage, "jpg", new File(destDir, sfile
                .getName()));
    }

    public static boolean isBlack(int colorInt)
    {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300)
        {
            return true;
        }
        return false;
    }

    public static boolean isWhite(int colorInt)
    {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 300)
        {
            return true;
        }
        return false;
    }

    public static int isBlackOrWhite(int colorInt)
    {
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730)
        {
            return 1;
        }
        return 0;
    }

    public static int getColorBright(int colorInt)
    {
        Color color = new Color(colorInt);
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    public static int ostu(int[][] gray, int w, int h)
    {
        int[] histData = new int[w * h];
        // Calculate histogram
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                int red = 0xFF & gray[x][y];
                histData[red]++;
            }
        }

        // Total number of pixels
        int total = w * h;

        float sum = 0;
        for (int t = 0; t < 256; t++)
            sum += t * histData[t];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++)
        {
            wB += histData[t]; // Weight Background
            if (wB == 0)
                continue;

            wF = total - wB; // Weight Foreground
            if (wF == 0)
                break;

            sumB += (float) (t * histData[t]);

            float mB = sumB / wB; // Mean Background
            float mF = (sum - sumB) / wF; // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax)
            {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }
}
