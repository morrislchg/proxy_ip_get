/**
 * Copyright (C), 2017-2018, lc
 * FileName: ImageProgress
 * Author:   mixlc
 * Date:     2018/1/3 0003 13:31
 * Description: 操作图片
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈操作图片〉
 *
 * @author mixlc
 * @create 2018/1/3 0003
 * @since 1.0.0
 */
public class ImageProgress {
    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }
    public static List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        List<Integer> weightlist = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (isBlack(img.getRGB(x, y)) == 1) {
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
    public static BufferedImage removeBlank(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        int start = 0;
        int end = 0;
        Label1: for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (isBlack(img.getRGB(x, y)) == 1) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                if (isBlack(img.getRGB(x, y)) == 1) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }
}
