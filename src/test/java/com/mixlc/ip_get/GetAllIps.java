/**
 * Copyright (C), 2017-2018, lc
 * FileName: GetAllIps
 * Author:   mixlc
 * Date:     2018/1/12 0012 17:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get;

import com.mixlc.ip_get.kuaidaili.KuaiDaiLiRunable;
import com.mixlc.ip_get.xicidaili.XiCiDaiLiRunable;
import com.mixlc.ip_get.yundaili.YunDailiRunable;
import com.mixlc.ip_get.zhandaye.ZhanDaYeRunable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class GetAllIps {
    public static void main(String[] args) {
        ZhanDaYeRunable zhanDaYeRunable = new ZhanDaYeRunable();
        Thread zhandaye = new Thread(zhanDaYeRunable,"站大爷====");
        YunDailiRunable yunDailiRunable = new YunDailiRunable();
        Thread yundaili = new Thread(yunDailiRunable,"云代理====");
        XiCiDaiLiRunable xiCiDaiLiRunable = new XiCiDaiLiRunable();
        Thread xicidaili = new Thread(xiCiDaiLiRunable,"西刺代理===");
        KuaiDaiLiRunable kuaiDaiLiRunable = new KuaiDaiLiRunable();
        Thread kuaidaili = new Thread(kuaiDaiLiRunable,"快代理====");
        try{
            zhandaye.start();
        }catch (Exception e){
            System.out.println("站大爷网站失败");
        }    try{
            yundaili.start();
        }catch (Exception e){
            System.out.println("云代理网站失败");
        }    try{
            xicidaili.start();
        }catch (Exception e){
            System.out.println("西刺代理网站失败");
        }try{
            kuaidaili.start();
        }catch (Exception e){
            System.out.println("快代理网站失败");
        }




    }
}
