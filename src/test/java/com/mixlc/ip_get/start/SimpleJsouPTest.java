/**
 * Copyright (C), 2017-2017, lc
 * FileName: SimpleJsouPTest
 * Author:   mixlc
 * Date:     2017/12/23 0023 14:48
 * Description: 测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.start;

import org.junit.Test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈测试〉
 *
 * @author mixlc
 * @create 2017/12/23 0023
 * @since 1.0.0
 */
public class SimpleJsouPTest {
    @Test
    public void getip(){
        String ip = "http://www.kuaidaili.com/free/inha/";
        SimpleJsouP simpleJsouP = new SimpleJsouP();
        simpleJsouP.get_usr(ip);
    }
}
