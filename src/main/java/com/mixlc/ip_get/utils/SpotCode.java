/**
 * Copyright (C), 2017-2018, lc
 * FileName: SpotCode
 * Author:   mixlc
 * Date:     2018/1/11 0011 10:54
 * Description: 斑点类验证码
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
 * 〈斑点类验证码〉
 *
 * @author mixlc
 * @create 2018/1/11 0011
 * @since 1.0.0
 */
public class SpotCode {
    private File file;
    private Map<String, String> cmap = new HashMap<String, String>();
    private Map<BufferedImage,String> trainMap=null;
    public SpotCode(File file) {
        this.file = file;
    }

    public String getCode() throws Exception {
        String filepath = file.getPath();
        String jpgpath = getJpgOraginalFileName(file.getName());
        File jpgfile = File.createTempFile(getPreName(jpgpath), getSubfixName(jpgpath));
        jpgfile.deleteOnExit();
        BmpReader.bmpTojpg(filepath, jpgfile.getPath());

        BufferedImage bufferedImage = removeBackgroud1(jpgfile);
        Map<BufferedImage,String> map = loadTrainData();
        List<BufferedImage> list = splitImage(bufferedImage);
        String name = getNameNumber();
        int i=0;
        String result = "";
        for(BufferedImage bufferedImage1:list){
        //    saveSinglecode1(bufferedImage1,name.charAt(i));
            result += getSingleCharOcr(bufferedImage1, map);
            i++;
        }
      //  saveJpg1(jpgfile, result);
        return result;
    }
    public Map<BufferedImage,String> loadTrainData() throws IOException {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            String path = PathUtils.getSpotPath();
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
    public String getNameNumber(){
        String name = file.getName();
        return name.substring(name.lastIndexOf("_")+1,name.indexOf("."));
    }
    public void saveSinglecode1(BufferedImage bi, char number) throws IOException {
        System.out.println("==========================================");
        System.out.println("11111111111111111111111");
        Date date = new Date();
        Random random = new Random();
        String preName = String.valueOf(date.getTime()) + "_" + random.nextInt(47);
        System.out.println(preName);
          File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a"+"\\"+getFileName()+"\\"+preName+".jpg");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        ImageIO.write(bi, "jpeg", file2);
    }

    public String getJpgOraginalFileName(String filename) {
        String name = filename.substring(0, filename.lastIndexOf(".")) + ".jpg";
        return name;
    }

    public String getPreName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    public String getSubfixName(String name) {
        return name.substring(name.lastIndexOf("."), name.length());
    }

    public void saveJpg1(File jpgFile, String result) throws IOException {
        Date date = new Date();
        String preName = String.valueOf(date.getTime());
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a"+"\\"+getFileName()+"\\"+preName+"_"+result+".jpg");
        //File file2 = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a" + "\\" + preName + "_" + result + ".jpg");
        FileUtils.copyFile(jpgFile, file2);
    }

    public String getFileName() {
        String fileName = this.file.getName();
        fileName = fileName.substring(0, fileName.indexOf("."));
        return fileName;
    }

    public BufferedImage removeBackgroud(File jpgfile)
            throws Exception {
        BufferedImage img = ImageIO.read(jpgfile);
        int width = img.getWidth();
        int height = img.getHeight();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isWhite(img.getRGB(x, y)) == 1)
                    continue;
                if (map.containsKey(img.getRGB(x, y))) {
                    map.put(img.getRGB(x, y), map.get(img.getRGB(x, y)) + 1);
                } else {
                    map.put(img.getRGB(x, y), 1);
                }
            }
        }
        int max = 0;
        int colorMax = 0;
        for (Integer color : map.keySet()) {
            if (max < map.get(color)) {
                max = map.get(color);
                colorMax = color;
                System.out.println(colorMax);
                Color colorm = new Color(colorMax);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (img.getRGB(x, y) != colorMax) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return img;
    }

    public boolean isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return true;
        }
        return false;
    }

    public BufferedImage removeBackgroud1(File jpgfile)
            throws Exception {
        BufferedImage img = ImageIO.read(jpgfile);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean flag = false;
                if (isBlack(img.getRGB(x, y))) {
                    if (x == 0 || x == width - 1) {
                        flag = true;
                        //上下
//                        if (isWhite(img.getRGB(x, y + 1)) == 1 && isWhite(img.getRGB(x, y - 1)) == 1) {
//                            flag = true;
//                        }
                    } else if (y == 0 || y == height - 1) {
                        flag = true;

//                        if (isWhite(img.getRGB(x - 1, y)) == 1 && isWhite(img.getRGB(x + 1, y)) == 1) {
//                            flag = true;
//                        }
                    } else {
                        int k = 0;
                        //左右均为空时，去掉此点
                        if (isWhite(img.getRGB(x - 1, y)) == 1) {
                            k++;
                        }
                        if (isWhite(img.getRGB(x + 1, y)) == 1) {
                            k++;
                        }
                        //上下均为空时，去掉此点
                        if (isWhite(img.getRGB(x, y + 1)) == 1) {
                            k++;
                        }
                        if (isWhite(img.getRGB(x, y - 1)) == 1) {
                            k++;
                        }
                        //斜上下为空时，去掉此点
                        if (isWhite(img.getRGB(x - 1, y + 1)) == 1) {
                            k++;
                        }
                        if (isWhite(img.getRGB(x + 1, y - 1)) == 1) {
                            k++;
                        }
                        //斜上下为空时，去掉此点
                        if (isWhite(img.getRGB(x + 1, y + 1)) == 1) {
                            k++;
                        }
                        if (isWhite(img.getRGB(x - 1, y - 1)) == 1) {
                            k++;
                        }
                        if (k >= 7) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        img.setRGB(x, y, -1);
                    }
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        return img;
    }

    public int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() >= 100) {
            return 1;
        }
        return 0;
    }
    public java.util.List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        java.util.List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        java.util.List<Integer> weightlist = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (isBlack(img.getRGB(x, y))) {
                    count++;
                }
            }
            weightlist.add(count);
        }
        for (int i = 0; i < weightlist.size();i++) {
            int length = 0;
            while (i < weightlist.size() && weightlist.get(i) > 0) {
                i++;
                length++;
            }
            if (length > 2) {
                subImgs.add(removeBlank(img.getSubimage(i - length, 0,
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
            for (int x = 0; x < width; ++x) {
                if (isBlack(img.getRGB(x, y))) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                if (isBlack(img.getRGB(x, y))) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }
}
