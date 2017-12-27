/**
 * Copyright (C), 2017-2017, lc
 * FileName: IpGetTest
 * Author:   mixlc
 * Date:     2017/12/27 0027 9:43
 * Description: 西刺代理测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.xicidaili;

import org.junit.Test;

/**
 * 〈一句话功能简述〉<br> 
 * 〈西刺代理测试〉
 *
 * @author mixlc
 * @create 2017/12/27 0027
 * @since 1.0.0
 */
public class IpGetTest {
    @Test
    public void getIP(){
        for(int i=1;i<6;i++){
            IpGet ipGet = new IpGet();
            String url = "http://www.xicidaili.com/nn/"+i;
            ipGet.getDomByUrl(url);
        }
    }
}
