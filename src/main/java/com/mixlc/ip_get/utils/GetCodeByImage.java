/**
 * Copyright (C), 2017-2018, lc
 * FileName: GetCodeByImage
 * Author:   mixlc
 * Date:     2018/1/4 0004 15:22
 * Description: 根据验证码图片返回验证码
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈根据验证码图片返回验证码〉
 *
 * @author mixlc
 * @create 2018/1/4 0004
 * @since 1.0.0
 */
public class GetCodeByImage {
    private File file;
    private BufferedImage bufferedImage;
    public GetCodeByImage(File file) {
        this.file = file;
    }
    public String getCode() throws Exception {
        fileToImageBuffer();
        String size = getSizeString();
        if("64_20".equals(size)){
            System.out.println("blueinput=========================");
            BlueCode blueCode = new BlueCode(file);
            return blueCode.getCode();
        }else if("38_29".equals(size)||"52_29".equals(size)||"66_29".equals(size)||"80_29".equals(size)){
            System.out.println("balck========================="+size);
            BlackColor blackColor = new BlackColor(file);
            return blackColor.getCode();
        }else{
            return "0";
        }
    }
    public String getSizeString(){
        return this.bufferedImage.getWidth()+"_"+this.bufferedImage.getHeight();
    }
    public void fileToImageBuffer() throws IOException {
        System.out.printf(file.getPath());
        BufferedImage image = ImageIO.read(file);
        this.bufferedImage = image;
    }
}
