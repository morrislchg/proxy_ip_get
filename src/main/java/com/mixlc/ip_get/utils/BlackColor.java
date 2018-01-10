/**
 * Copyright (C), 2017-2018, lc
 * FileName: BlackColor
 * Author:   mixlc
 * Date:     2018/1/4 0004 16:49
 * Description: 黑色花底识别
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈黑色花底识别〉
 *
 * @author mixlc
 * @create 2018/1/4 0004
 * @since 1.0.0
 */
public class BlackColor {
    private File file;
    private File jpgFile;
    private boolean testflag = false;
    private Map<BufferedImage,String> trainMap = null;

    public BlackColor(File file) {
        this.file = file;
    }

    public BlackColor() {
    }

    public void getJpgFile() throws IOException {
        File file1 = File.createTempFile("jpgfile",".jpg");
        try {
            GifDecoder decoder = new GifDecoder();
            decoder.read(file.getPath());
            BufferedImage frame = decoder.getFrame(0); //得到帧
            file1.deleteOnExit();
            FileOutputStream out = new FileOutputStream(file1);
            ImageIO.write(frame, "jpeg", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println( "splitGif Failed!");
            e.printStackTrace();
        }
        jpgFile = file1;
    }
    public String getCode() throws Exception {
        getJpgFile();
        BufferedImage bufferedImage = removeBackgroud();
        List<BufferedImage> list = splitImage(bufferedImage);
        Map<BufferedImage,String> map = getTrainMap();
        String result = "";
        try{
            for (BufferedImage bi : list) {
                if(testflag){
                    saveSinglecode(bi);
                }
                saveSinglecode1(bi);
                result += getSingleCharOcr(bi, map);
            }
            if(testflag){
                renameFile(result);
            }
            renameFile1(result);
        }catch (Exception e){
            result = "0";
        }

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
    public void renameFile(String result){
        Date date = new Date();
        String preName = String.valueOf(date.getTime());
        File file2 = new File(jpgFile.getParentFile().getPath()+"\\"+getFileName()+"\\"+preName+"_"+result+".gif");
//        if(!file2.exists()){
//            file2.mkdirs();
//        }
        jpgFile.renameTo(file2);
    }
    public void renameFile1(String result) throws IOException {
        Date date = new Date();
        String preName = String.valueOf(date.getTime());
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a"+"\\"+getFileName()+"\\"+preName+"_"+result+".gif");
//        if(!file2.exists()){
//            file2.mkdirs();
//        }
        FileUtils.copyFile(jpgFile,file2);
    }
    public String getSingleCharOcr(BufferedImage img,
                                   Map<BufferedImage, String> map) {
        String result = "";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            int widthmin = width < bi.getWidth() ? width : bi.getWidth();
            int heightmin = height < bi.getHeight() ? height : bi.getHeight();
            if(!sizeMatch(img,bi)){
                continue;
            }
            Label1: for (int x = 0; x < widthmin; ++x) {
                for (int y = 0; y < heightmin; ++y) {
                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }
    // 计算 尺寸匹配度如果尺寸差别过大直接pass
    public boolean sizeMatch(BufferedImage souce,BufferedImage target){
        int width = souce.getWidth();
        int height = souce.getHeight();
        int bw = width < target.getWidth() ? target.getWidth()/width : width/target.getWidth();
        if(bw>2){
            return false;
        }
        int hw = height < target.getHeight() ? target.getWidth()/height : height/target.getWidth();
        if(hw>2){
            return false;
        }
        return true;
    }
    public Map<BufferedImage,String> getTrainMap() throws IOException {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            String path = PathUtils.getBlackPath();
            File dir = new File(path);
            File[] files = dir.listFiles();
            for (File file : files) {
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        return trainMap;
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
                if (isWhite(img.getRGB(x, y)) != 1) {
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
            if (length > 15) {
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
    public BufferedImage removeBlank(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        int start = 0;
        int end = 0;
        Label1: for (int y = 0; y < height; ++y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) != 1) {
                    count++;
                }
                if (count >= 2) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) != 1) {
                    count++;
                }
                if (count >= 2) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
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
    public BufferedImage removeBackgroud()
            throws Exception {
        BufferedImage img = ImageIO.read(this.jpgFile);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        for(int y = 1; y < height-1; y++){
            for(int x = 1; x < width-1; x++){
                boolean flag = false ;
                if(isBlack(img.getRGB(x, y))){
                    //上下均为空去掉
//                    if(isWhite(img.getRGB(x-1, y))==1 && isWhite(img.getRGB(x+1, y))==1){
//                        flag = true;
//                    }
                    // 上下左右均为空去掉
//                    if(isWhite(img.getRGB(x, y+1))==1 && isWhite(img.getRGB(x, y-1))==1){
//                        flag = true;
//                    }
                    if(isWhite(img.getRGB(x, y+1))==1 && isWhite(img.getRGB(x, y-1))==1&&isWhite(img.getRGB(x-1, y))==1 && isWhite(img.getRGB(x+1, y))==1){
                        flag = true;
                    }
                    if(flag){
                        img.setRGB(x,y,-1);
                    }
                }
            }
        }

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (isWhite(img.getRGB(x, y))== 1)
                {
                    System.out.print(" ");
                } else
                {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        return img;
    }
    public int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 70) {
            return 1;
        }
        return 0;
    }

}
