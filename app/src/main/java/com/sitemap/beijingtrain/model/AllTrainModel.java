package com.sitemap.beijingtrain.model;

/**
 * @desc 获取所有列车车次
 * Created by chenmeng on 2016/10/18.
 */
public class AllTrainModel {
    private String train_id;//车次train_id
    private String number;//车次编号

    public String getTrain_id() {
        return train_id;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
