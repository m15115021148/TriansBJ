package com.sitemap.beijingtrain.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sitemap.beijingtrain.fragment.HomePageFragment;
import com.sitemap.beijingtrain.model.TemperatureModel;
import com.sitemap.beijingtrain.model.TimeModel;
import com.sitemap.beijingtrain.model.TimesListModel;
import com.sitemap.beijingtrain.model.TypeModel;

/**
 *首页数据fragment的adapter
 * @author chenhao
 * @date 2016-12-14
 */
public class MpagerAdapter extends FragmentPagerAdapter {
//时间数据集合
    private List<TimesListModel> lTimeModel;
    private String trainId;
    private String type;
    public static String TIME="time";
    public static String TYPES="typeStatus";
    public static String TRAIN_ID="trainId";
    public HomePageFragment currentFragment;//当前显示fragment

    public MpagerAdapter(FragmentManager fm, String trainId, String type, List<TimesListModel> lTimeModel) {
        super(fm);
        this.lTimeModel=lTimeModel;
        this.trainId=trainId;
        this.type=type;
        Log.e("result","this.type:"+this.type);
    }

    private TypeModel getData(int position){
        TypeModel model = new TypeModel();
        model.setTrainID(trainId);
        model.setType(type);
        model.setTime(lTimeModel.get(position).getTime());
        return model;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new HomePageFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("TypeModel",getData(position));
//        String time=lTimeModel.get(position).getTime();
//        bundle.putString("time",time);
//        bundle.putString("type", type);
//        bundle.putString("trainID",trainId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment=(HomePageFragment)object;
        super.setPrimaryItem(container, position, object);
    }

    public HomePageFragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public int getCount() {
        return     lTimeModel == null ? 0 : lTimeModel.size();
    }

}
