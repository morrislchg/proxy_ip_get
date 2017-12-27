/**
 * Copyright (C), 2017-2017, lc
 * FileName: MainTest
 * Author:   mixlc
 * Date:     2017/12/27 0027 10:18
 * Description: 主测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.mainTest;

import com.mixlc.ip_get.kuaidaili.IpGetTest;
import com.mixlc.ip_get.yundaili.ip_getTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 〈一句话功能简述〉<br> 
 * 〈主测试类〉
 *
 * @author mixlc
 * @create 2017/12/27 0027
 * @since 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({IpGetTest.class, com.mixlc.ip_get.xicidaili.IpGetTest.class,ip_getTest.class})
public class MainTest {

}
