package com.sitemap.beijingtrain.model;

/**
 * @desc 线形图数据
 * Created by chenmeng on 2016/10/21.
 */
public class LineChartModel {
    private String time;//时间
    private String wNum;//告警数量
    private String nNum;//普通数量；
    private String eNum;//故障数量

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getwNum() {
        return wNum;
    }

    public void setwNum(String wNum) {
        this.wNum = wNum;
    }

    public String getnNum() {
        return nNum;
    }

    public void setnNum(String nNum) {
        this.nNum = nNum;
    }

    public String geteNum() {
        return eNum;
    }

    public void seteNum(String eNum) {
        this.eNum = eNum;
    }
}
