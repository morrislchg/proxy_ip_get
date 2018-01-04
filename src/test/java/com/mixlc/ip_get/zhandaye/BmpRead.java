/**
 * Copyright (C), 2017-2018, lc
 * FileName: BmpRead
 * Author:   mixlc
 * Date:     2018/1/3 0003 11:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/3 0003
 * @since 1.0.0
 */
public class BmpRead {
    /**
     * @param_args
     * @return
     * @throws IOException
     */

    public int[][] ReadBMPPic(String src) throws IOException
    {
        FileInputStream fis=new FileInputStream(src);
        BufferedInputStream bis=new BufferedInputStream(fis);

        //丢掉文件头信息
        bis.skip(18);

        //获取长度与宽度
        byte[] b1=new byte[4];
        bis.read(b1);
        byte[] b2=new byte[4];
        bis.read(b2);

        int Width=byte2Int(b1);
        int Height=byte2Int(b2);
        System.out.println("Hight:"+Height+" Width:"+Width);

        //因为bmp位图的读取顺序为横向扫描，所以应把数组定义为int[Height][Width]
        int[][] data=new int[Height][Width];
        int skipnum=0;

        //bmp图像区域的大小必须为4的倍数，而它以三个字节存一个像素，读的是偶应当跳过补上的0
        if(Width*3%4!=0)
        {
            skipnum=4-Width*3%4;
        }
        System.out.println(skipnum);

        bis.skip(28);

        for(int i=0;i<data.length;i++)
        {
            for(int j=0;j<data[i].length;j++)
            {
                int red=bis.read();
                int green=bis.read();
                int blue=bis.read();
            }
            if(skipnum!=0)
            {
                bis.skip(skipnum);
            }
        }

        bis.close();
        return data;
    }
    private int byte2Int(byte[] b) throws IOException {
        // TODO Auto-generated method stub
        int num= (b[3]&0xff<<24)|(b[2]&0xff)<<16|(b[1]&0xff)<<8|b[0]&0xff;
        return num;
    }

    @Test
    public void read() throws IOException {
        int[][] array =  ReadBMPPic("C:\\Users\\Administrator\\Desktop\\imgs\\66_29_a\\blue\\m_0aeab1984a7ec77e.gif");
        for(int i=0;i<array.length;i++){
            int[] arr = array[i];
            System.out.println(Arrays.toString(arr));
        }
    }
}
