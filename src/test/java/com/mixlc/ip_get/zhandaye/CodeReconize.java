/**
 * Copyright (C), 2017-2018, lc
 * FileName: CodeReconize
 * Author:   mixlc
 * Date:     2018/1/2 0002 16:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import com.mixlc.ip_get.utils.GifDecoder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/2 0002
 * @since 1.0.0
 */
public class CodeReconize {
    private Map<BufferedImage,String> trainMap = null;
    public int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
            return 1;
        }
        return 0;
    }

    public BufferedImage removeBackgroud(String picFile)
            throws Exception {
        BufferedImage img = ImageIO.read(new File(picFile));
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
        return img;
    }

    public Map<BufferedImage,String> getTrainMap() throws IOException {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            File dir = new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\新建文件夹\\");
            File[] files = dir.listFiles();
            for (File file : files) {
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        return trainMap;
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
    public String testGif(String gifName){
        //String gifName = "m_00cdadd4d169acfb.gif";
     //   String gifName = "m_3fde3f03632febd3.gif";
        String path = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\";
        String name = "";
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
            name = subPic[0];
        } catch (Exception e) {
            System.out.println( "splitGif Failed!");
            e.printStackTrace();
            System.out.println("----");
        }
        return name;
    }

    @Test
    public void reconizeTest(){
        //String path = "C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\0.jpg";
        String path = testGif("m_29ae92f9ed4eb8ee.gif");
        try {
            BufferedImage bufferedImage = removeBackgroud(path);
       //     ImageIO.write(bufferedImage,"jpeg",new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\sp_p.jpg"));

            List<BufferedImage> list = splitImage(bufferedImage);
//            for(int i=0;i<list.size();i++){
//                ImageIO.write(list.get(i),"jpeg",new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\sp_"+i+".jpg"));
//            }
            Map<BufferedImage,String> map = getTrainMap();
            String result = "";
            for (BufferedImage bi : list) {
                result += getSingleCharOcr(bi, map);
            }
            ImageIO.write(bufferedImage, "JPG", new File("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\" + result + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (count >= 3) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }

}
