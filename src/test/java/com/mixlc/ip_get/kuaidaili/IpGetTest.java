/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGetTest
 * Author:   mixlc
 * Date:     2017/12/23 0023 15:44
 * Description: 获取ip测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.kuaidaili;

import org.junit.Test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈获取ip测试〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class IpGetTest {
    @Test
    public void getDomByUrl(){
        String url = "http://www.kuaidaili.com/free/inha/";
        IpGet ipGet = new IpGet();
        ipGet.getDomByUrl(url);
    }
}
