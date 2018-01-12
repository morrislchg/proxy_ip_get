/**
 * Copyright (C), 2017-2018, lc
 * FileName: TicketThreadR
 * Author:   mixlc
 * Date:     2018/1/12 0012 12:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.zhandaye;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author mixlc
 * @create 2018/1/12 0012
 * @since 1.0.0
 */
public class TicketThreadR implements Runnable {

    private int num = 5;            //总共票数设定为5张

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            sale();                    //调用同步方法
        }
    }

    //使用同步方法
    public synchronized void sale(){
        try {
            Thread.sleep(300);    //休息300毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(this.num>0){
            //打印买票信息
            System.out.println(Thread.currentThread().getName() + "买票: " + this.num--);
        }
    }

    public static void main(String[] args) {
        TicketThreadR ticketThread = new TicketThreadR();

        new Thread(ticketThread,"售票口一").start();    //线程一
        new Thread(ticketThread,"售票口二").start();    //线程一
        new Thread(ticketThread,"售票口三").start();    //线程一
    }
}
