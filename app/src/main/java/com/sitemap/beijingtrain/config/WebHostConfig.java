package com.sitemap.beijingtrain.config;

/**
 * @author chenhao
 * @ClassName: WebHostConfig.java
 * @Description: 网络ip、port配置文件
 * @Date 2015-11-14
 */

public class WebHostConfig {

//    private static final String HOST_ADDRESS = "http://192.168.1.56:1599/";
    private static final String HOST_ADDRESS = "http://218.202.235.66:1599/";//外网
//    private static final String HOST_ADDRESS = "http://121.40.212.11:20015/";//外网
    private static final String HOST_NAME = HOST_ADDRESS + "RailwayMonitorTemp/";

    public static String getHostAddress() {
        return HOST_ADDRESS;
    }

    public static String getHostName() {
        return HOST_NAME;
    }


}
