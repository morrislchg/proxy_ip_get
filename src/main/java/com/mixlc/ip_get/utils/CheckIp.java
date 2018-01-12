/**
 * Copyright (C), 2017-2018, lc
 * FileName: CheckIp
 * Author:   mixlc
 * Date:     2018/1/10 0010 14:02
 * Description: 检测代理ip是否有效
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mixlc.ip_get.utils;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈检测代理ip是否有效〉
 *
 * @author mixlc
 * @create 2018/1/10 0010
 * @since 1.0.0
 */
public class CheckIp {
    /**
     * 批量代理IP有效检测
     *
     * @param proxyIpMap
     * @param reqUrl
     */
    public static void checkProxyIp(Map<String, Integer> proxyIpMap, String reqUrl) throws IOException {

        for (String proxyHost : proxyIpMap.keySet()) {
            Integer proxyPort = proxyIpMap.get(proxyHost);

            int statusCode = 0;
            //设置代理IP、端口、协议（请分别替换）
            HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");

            //把代理设置到请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();

            //实例化CloseableHttpClient对象
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

            //访问目标地址
            HttpGet httpGet;
            httpGet = new HttpGet(reqUrl);

            //请求返回
            CloseableHttpResponse httpResp = httpclient.execute(httpGet);
            try {
                statusCode = httpResp.getStatusLine().getStatusCode();
            } catch (Exception e) {

            } finally {
                httpResp.close();
            }
            System.out.format("%s:%s-->%s\n", proxyHost, proxyPort, statusCode);
        }
    }   public static String checkProxyIpOneByOne(Map<String, String> proxyIpMap, String reqUrl) throws IOException {

            String proxyHost = proxyIpMap.get("ip");
            Integer proxyPort = Integer.valueOf(proxyIpMap.get("port"));

            int statusCode = 0;
            //设置代理IP、端口、协议（请分别替换）
            HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");

            //把代理设置到请求配置
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setProxy(proxy).setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();

            //实例化CloseableHttpClient对象
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

            //访问目标地址
            HttpGet httpGet;
            httpGet = new HttpGet(reqUrl);
        //请求返回
             CloseableHttpResponse httpResp = httpclient.execute(httpGet);
            try {
                statusCode = httpResp.getStatusLine().getStatusCode();
            } catch (Exception e) {

            } finally {
                httpResp.close();
            }
            System.out.format("%s:%s-->%s\n", proxyHost, proxyPort, statusCode);
            if(statusCode==200){
                return proxyHost+":"+proxyPort;
            }else{
                return "";
            }
    }

    /**
     * 代理IP有效检测
     *
     * @param proxyIp
     * @param proxyPort
     * @param reqUrl
     */
    public static void checkProxyIp(String proxyIp, int proxyPort, String reqUrl) throws IOException {
        Map<String, Integer> proxyIpMap = new HashMap<String, Integer>();
        proxyIpMap.put(proxyIp, proxyPort);
        checkProxyIp(proxyIpMap, reqUrl);
    }

    public static void main(String[] args) throws IOException {

        Map<String, Integer> proxyIpMap = new HashMap<String, Integer>();
        proxyIpMap.put("111.47.221.65", 8081);
        proxyIpMap.put("183.232.188.103", 8080);

        checkProxyIp(proxyIpMap, "http://www.blogjava.net/zjusuyong/articles/304788.html");

    }
}
