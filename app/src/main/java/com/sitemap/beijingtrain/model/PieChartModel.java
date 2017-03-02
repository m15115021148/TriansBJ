package com.sitemap.beijingtrain.model;

/**
 * @desc 饼形图数据
 * Created by chenmeng on 2016/10/21.
 */
public class PieChartModel {
    private String count;
    private String type;//1正常数据，2故障，3告警

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
