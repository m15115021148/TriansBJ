package com.sitemap.beijingtrain.model;

/**
 * Created by Administrator on 2016/11/17.
 */
public class PositionModel {
    private String lng;//             经度
    private String lat;//               纬度
    private String speed;//            速度
    private String direction;//          方向
    private String time;//时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
