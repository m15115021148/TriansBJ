package com.sitemap.beijingtrain.model;

import java.io.Serializable;

/**
 * @desc   组合数据 传递 实体类
 * Created by chenmeng on 2017/2/28.
 */

public class TypeModel implements Serializable{
    private String time;
    private String type;
    private String trainID;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }
}
