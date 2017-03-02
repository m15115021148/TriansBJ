package com.sitemap.beijingtrain.config;

/**
 * com.sitemap.wisdomjingjiang.config.RequestCode
 *
 *
 * @author chenmeng
 *         接口请求需要用的辨识常量
 *         create at 2016年4月26日 上午9:30:35
 */
public class RequestCode {

    //	string类型常量
    public static final String ERRORINFO = "服务器无法连接，请稍后再试！";//网络连接错误信息
    public static final String NOLOGIN = "网络无法连接！";//网络无法连接

    /**注册规则*/
    public static final String REGISTERTOOT = "密码长度应在6-16位，必须是字母跟数字组合";

    //	int类型常量
    public static final int REGISTER = 0x0001;//注册常量
    public static final int LOGIN = 0x0002;//登录常量

    public static final int GETALLTRAIN = 0x0003;//获取所有列车车次
    public static final int QUERYTEMPERATURE = 0x0004;//查询某个条件下的车厢温度
    public static final int GETTIMES = 0x0005;//获取某一段时间的时间点

    public static final int FLAGCHECK = 0x0006;//返回结果标识符 条件查询
    public static final int GETCHARTSDATA = 0x0007;//获得统计图信息
    public static final int SEARCHPOSITION=0x0008;//查询地理位置
    public static final int SEARCHPOSITIONTRACK=0x0009;//查询地理轨迹
    public static final int CHANGE = 0x0010;//时间点滑动
}
