/**
 * Copyright (C), 2017-2018, lc
 * FileName: BlueCode
 * Author:   mixlc
 * Date:     2018/1/4 0004 15:28
 * Description: 蓝色验证码识别
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.mixlc.ip_get.ImageProProgress.ImagePreProcess3.getSingleCharOcr;

/**
 * 〈一句话功能简述〉<br> 
 * 〈蓝色验证码识别〉
 *
 * @author mixlc
 * @create 2018/1/4 0004
 * @since 1.0.0
 */
public class BlueCode {
    private File file;
    private boolean testflag = false;
    private Map<BufferedImage,String> trainMap = null;
    public BlueCode(File file) {
        this.file = file;
    }
    public String getCode() throws Exception {
        String filepath = file.getPath();
        String jpgpath = getJpgOraginalFileName(file.getName());
        File jpgfile = File.createTempFile(getPreName(jpgpath),getSubfixName(jpgpath));
        jpgfile.deleteOnExit();
        BmpReader.bmpTojpg(filepath, jpgfile.getPath());
        BufferedImage bufferedImage = cleanLinesInImage(jpgfile);
        Map<BufferedImage,String> map = loadTrainData();
        List<BufferedImage> list = ImageProgress.splitImage(bufferedImage);
        String result = "";
        for (BufferedImage bi : list) {
            if(testflag){
                saveSinglecode(bi);
            }
           // saveSinglecode1(bi);
            result += getSingleCharOcr(bi, map);
        }
        if(testflag){
            saveJpg(jpgfile,result);
        }
     //   saveJpg1(jpgfile,result);
        return result;
    }
    public void saveSinglecode(BufferedImage bi) throws IOException {
        System.out.println("==========================================");
        System.out.println("11111111111111111111111");
        Date date = new Date();
        Random random = new Random();
        String preName = String.valueOf(date.getTime()) +"_"+ random.nextInt(47);
        System.out.println(preName);
        File file2 = new File(file.getParentFile().getPath()+"\\"+getFileName()+"\\"+preName+".jpg");
        if(!file2.exists()){
            file2.mkdirs();
        }
        ImageIO.write(bi, "jpeg", file2);
    }
    public void saveSinglecode1(BufferedImage bi) throws IOException {
        System.out.println("==========================================");
        System.out.println("11111111111111111111111");
        Date date = new Date();
        Random random = new Random();
        String preName = String.valueOf(date.getTime()) +"_"+ random.nextInt(47);
        System.out.println(preName);
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a"+"\\"+getFileName()+"\\"+preName+".jpg");
        if(!file2.exists()){
            file2.mkdirs();
        }
        ImageIO.write(bi, "jpeg", file2);
    }
    public String getFileName(){
        String fileName = this.file.getName();
        fileName = fileName.substring(0,fileName.indexOf("."));
        return fileName;
    }
    public void saveJpg(File jpgFile,String result){
        Date date = new Date();
        String preName = String.valueOf(date.getTime());
        File file2 = new File(jpgFile.getParentFile().getPath()+"\\"+getFileName()+"\\"+preName+"_"+result+".jpg");
        jpgFile.renameTo(file2);
    }
    public void saveJpg1(File jpgFile,String result) throws IOException {
        Date date = new Date();
        String preName = String.valueOf(date.getTime());
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a"+"\\"+getFileName()+"\\"+preName+"_"+result+".jpg");
        FileUtils.copyFile(jpgFile,file2);
    }
    public Map<BufferedImage,String> loadTrainData() throws IOException {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            String path = PathUtils.getBluePath();
            File dir = new File(path);
            File[] files = dir.listFiles();
            for (File file : files) {
                String name = file.getName();
                File[] files1 = file.listFiles();
                for(File file1:files1){
                    map.put(ImageIO.read(file1), name);
                }
            }
            trainMap = map;
        }
        return trainMap;
    }
    /**
     *
     * @param sfile
     *            需要去噪的图像
     *            去噪后的图像保存地址
     * @throws IOException
     */
    public BufferedImage cleanLinesInImage(File sfile)  throws IOException {
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
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                if(y==0||y==h-1||x==0||x==w-1){
                    binaryBufferedImage.setRGB(x,y,-1);
                }else{
                    continue;
                }
            }
        }
        //去除干扰线条
        for(int y = 1; y < h-1; y++){
            for(int x = 1; x < w-1; x++){
                boolean flag = false ;
                if(isBlack(binaryBufferedImage.getRGB(x, y))){

                    //左右均为空时，去掉此点
                    if(isWhite(binaryBufferedImage.getRGB(x-1, y)) && isWhite(binaryBufferedImage.getRGB(x+1, y))){
                        flag = true;
                    }
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
        return binaryBufferedImage;
    }


    public String getjpgNameByFileName(String filename){
        String name = filename.substring(filename.lastIndexOf("_")+1,filename.lastIndexOf(".")) + ".jpg";
        return name;
    }
    public String getJpgOraginalFileName(String filename){
        String name = filename.substring(0,filename.lastIndexOf(".")) + ".jpg";
        return name;
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
    public String getPreName(String name){
        return name.substring(0,name.lastIndexOf("."));
    }
    public String getSubfixName(String name){
        return name.substring(name.lastIndexOf("."),name.length());
    }
}
