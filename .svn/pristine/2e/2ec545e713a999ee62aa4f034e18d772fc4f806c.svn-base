package com.sitemap.beijingtrain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sitemap.beijingtrain.adapter.MpagerAdapter;
import com.sitemap.beijingtrain.fragment.HomePageFragment;

/**
 * Created by chenmeng on 2016/11/22.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {

    MpagerAdapter pagerAdapter;

    private boolean isSlide;//是否滑动中 默认没有开始滑动

    public MpagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(MpagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if(getPagerAdapter()==null){
            return false;
        }else {
            HomePageFragment fragment=   getPagerAdapter().getCurrentFragment();
            return fragment.canPullDown();
        }
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
