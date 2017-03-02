package com.sitemap.beijingtrain.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.sitemap.beijingtrain.model.AllTrainModel;
import com.sitemap.beijingtrain.util.NetworkUtil;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmeng on 2016/10/18.
 */
public class MyApplication extends Application{
    /**application对象*/
    private static MyApplication instance;
    public static NetworkUtil netState;//网络状态
    public static List<AllTrainModel> lTrainModel=new ArrayList<AllTrainModel>();
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        netState = new NetworkUtil(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
    }

    public static MyApplication instance() {
        if (instance != null) {
            return instance;
        } else {
            return new MyApplication();
        }
    }

    /**
     * 初始化xutils框架
     */
    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    /**
     * 获取手机网络状态对象
     *
     * @return
     */
    public static NetworkUtil getNetObject() {
        if (netState != null) {
            return netState;
        } else {
            return new NetworkUtil(instance().getApplicationContext());
        }
    }

    /**
     * 设置段落中个别字体的颜色
     * @param tv         textview
     * @param txt        段落
     * @param lable      设置字体的颜色
     */
    public static void setTextColor(TextView tv, String txt, String lable) {
        SpannableStringBuilder style = new SpannableStringBuilder(txt);
        String[] split = lable.split(";");
        if (split.length > 0) {
            for (int i = 0; i < split.length; i++) {
                if(txt.contains(split[i])){
                    int start = txt.indexOf(split[i]);
                    int end = start + split[i].length();
                    ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#1cace9"));
                    style.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    tv.setText(style);
                }
            }
        } else {
            tv.setText(style);
        }
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * listview没有数据显示 的控件
     * @param context 本类
     * @param T AbsListView
     * @param txt 内容
     */
    public static void setEmptyShowText(Context context, AbsListView T, String txt){
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText(txt);
        emptyView.setTextSize(18);
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)T.getParent().getParent().getParent().getParent()).addView(emptyView);
        T.setEmptyView(emptyView);
    }

}
