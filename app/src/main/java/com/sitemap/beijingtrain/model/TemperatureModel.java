package com.sitemap.beijingtrain.model;

/**
 * @desc 车厢温度
 * Created by chenmeng on 2016/10/18.
 */
public class TemperatureModel {
    private String train_number;//车次编号
    private String carriage_name;//车厢号
    private String carriage_number;//车厢编号
    private String tem1;//轴温1
    private String tem2;//
    private String tem3;//
    private String tem4;//
    private String tem5;//
    private String tem6;//
    private String tem7;//
    private String tem8;//轴温8
    private String temp;//环温
    private String time;//时间点（每条数据时间间隔为10min）
    private String type;//级别（普通《默认》1、故障2、报警3）
    private String lng;//经度
    private String lat;//纬度
    private String speed;//速度
    private String direction;//方向

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrain_number() {
        return train_number;
    }

    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    public String getCarriage_name() {
        return carriage_name;
    }

    public void setCarriage_name(String carriage_name) {
        this.carriage_name = carriage_name;
    }

    public String getCarriage_number() {
        return carriage_number;
    }

    public void setCarriage_number(String carriage_number) {
        this.carriage_number = carriage_number;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getTem3() {
        return tem3;
    }

    public void setTem3(String tem3) {
        this.tem3 = tem3;
    }

    public String getTem4() {
        return tem4;
    }

    public void setTem4(String tem4) {
        this.tem4 = tem4;
    }

    public String getTem5() {
        return tem5;
    }

    public void setTem5(String tem5) {
        this.tem5 = tem5;
    }

    public String getTem6() {
        return tem6;
    }

    public void setTem6(String tem6) {
        this.tem6 = tem6;
    }

    public String getTem7() {
        return tem7;
    }

    public void setTem7(String tem7) {
        this.tem7 = tem7;
    }

    public String getTem8() {
        return tem8;
    }

    public void setTem8(String tem8) {
        this.tem8 = tem8;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
