package com.sitemap.beijingtrain.model;

/**
 * Created by Administrator on 2016/10/19.
 */
public class TimeModel {
//添加默认值
    private String time="";//时间
    private int type=0;//显示颜色（0：没有被选中，1：被选中）

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
