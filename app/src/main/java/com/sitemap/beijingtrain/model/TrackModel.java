package com.sitemap.beijingtrain.model;

/**
 * Created by Administrator on 2016/11/23.
 */
public class TrackModel {
    private String lng;//             经度
    private String lat;//              纬度
    private String speed;//           速度
    private String time;//            时间点
    private String isStop;//          是否停车（0：不停车，1：停车）

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }
}
