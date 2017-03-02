package com.sitemap.beijingtrain.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.application.MyApplication;
import com.sitemap.beijingtrain.config.RequestCode;
import com.sitemap.beijingtrain.util.DateUtil;
import com.sitemap.beijingtrain.util.ToastUtil;
import com.sitemap.beijingtrain.view.NumericWheelAdapter;
import com.sitemap.beijingtrain.view.OnWheelChangedListener;
import com.sitemap.beijingtrain.view.WheelTimeView;
import com.sitemap.beijingtrain.view.WheelView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @desc 查询条件
 * @author  chenmeng
 */
public class CheckActivity extends BaseActivity implements View.OnClickListener{
    private CheckActivity mContext;//本类
    private RelativeLayout mTrains,mNumber,mSTime,mETime;//车次，车厢，起始时间，终止时间
    private LinearLayout mBack;//删除
    private TextView check_train,sure;//车次 查询
    // 选中位置
    private int trainSelect = 0;
    private Dialog dialog;//dialog
    private static int START_YEAR = 1999, END_YEAR = 2050;//起始年份，结束年份
    private TextView sTime,eTime;
    private String strTrians,strSTime,strETime,strTrains_id;//车次，开始时间，结束时间 车次id
    private int nowSelect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        setTitle("");//无标题 label属性
        // 设置 窗口的显示位置
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        mContext = this;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView(){
        mTrains = (RelativeLayout) findViewById(R.id.re_train);
        mTrains.setOnClickListener(this);
        mNumber = (RelativeLayout) findViewById(R.id.re_number);
        mNumber.setOnClickListener(this);
        mSTime = (RelativeLayout) findViewById(R.id.re_s_time);
        mSTime.setOnClickListener(this);
        mETime = (RelativeLayout) findViewById(R.id.re_e_time);
        mETime.setOnClickListener(this);
        mBack = (LinearLayout) findViewById(R.id.check_delete);
        mBack.setOnClickListener(this);
        check_train= (TextView) findViewById(R.id.check_train);
        sure = (TextView) findViewById(R.id.check_sure);
        sure.setOnClickListener(this);
        sTime = (TextView) findViewById(R.id.check_s_time);
        eTime = (TextView) findViewById(R.id.check_e_time);
        if (MyApplication.lTrainModel.size()>0){
            check_train.setText(MyApplication.lTrainModel.get(0).getNumber());
            strTrains_id = MyApplication.lTrainModel.get(0).getTrain_id();
            strTrians = MyApplication.lTrainModel.get(0).getNumber();
        }else{
            check_train.setText("请选择");
        }
        strSTime = DateUtil.getCurrentAgeTimes(24).replace(".","-");
        strETime = DateUtil.getNYRSFDates().replace(".","-");
        sTime.setText(strSTime);
        eTime.setText(strETime);
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            finish();
        }
        if (v == mTrains){//车次
           final String[] ltrain;
            if (MyApplication.lTrainModel.size()>0){
                ltrain=new String[MyApplication.lTrainModel.size()];
                for (int i = 0; i <MyApplication.lTrainModel.size() ; i++) {
                    ltrain[i]=MyApplication.lTrainModel.get(i).getNumber();
                }
            nowSelect=trainSelect;
            View outerView = LayoutInflater.from(this).inflate(
                    R.layout.wheel_view, null);
            WheelView wv = (WheelView) outerView
                    .findViewById(R.id.wheel_view_wv);
            wv.setOffset(2);
            wv.setItems(Arrays.asList(ltrain));
            wv.setSeletion(trainSelect);
            wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex,
                                       String item) {
                    // Log.d(TAG, "[Dialog]selectedIndex: " +
                    // selectedIndex + ", item: " + item);
                    trainSelect = selectedIndex - 2;
//                    refund_yuanyin.setText(ltrain[trainSelect]);
//                    Log.i("TAG","checi:"+ltrain[trainSelect]);
                    strTrians = ltrain[trainSelect];
                    strTrains_id = MyApplication.lTrainModel.get(trainSelect).getTrain_id();
                    check_train.setText(ltrain[trainSelect]);
                }
            });

            new AlertDialog.Builder(this).setTitle("选择车次")
                    .setView(outerView)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    trainSelect = nowSelect;
                    check_train.setText(ltrain[trainSelect]);
                    dialog.dismiss();
                }
            }).show();
            }else {
                ToastUtil.showBottomShort(this,"暂无车次信息");
            }
        }
        if(v == mNumber){//车厢
        }
        if(v == mSTime){//起始时间
            showDateTimePicker(1);
        }
        if(v == mETime){//终止时间
            showDateTimePicker(2);
        }
        if(v == sure){//查询
            if(TextUtils.isEmpty(strTrians)){
                ToastUtil.showBottomShort(mContext,"请选择车次");
                return;
            }
            if(TextUtils.isEmpty(strSTime)){
                ToastUtil.showBottomShort(mContext,"请选择起始时间");
                return;
            }
            if(TextUtils.isEmpty(strETime)){
                ToastUtil.showBottomShort(mContext,"请选择终止时间");
                return;
            }
            if (DateUtil.getTwoTimeInterval(strETime,strSTime)<0){
                ToastUtil.showBottomShort(mContext,"终止时间不能小于起始时间");
                return;
            }
            if (DateUtil.getTwoTimeInterval(strETime,strSTime)>15*24*60*60){
                ToastUtil.showBottomShort(mContext,"时间差最大为15天");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("trains",strTrians);
            intent.putExtra("s_time",strSTime);
            intent.putExtra("e_time",strETime);
            intent.putExtra("trains_id",strTrains_id);
            setResult(RequestCode.FLAGCHECK, intent);
            finish();
        }
    }


    /**
     * @Description: TODO 弹出日期时间选择器
     * @param typeBt 类型
     */
    private void showDateTimePicker(final int typeBt) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
        String[] months_little = { "4", "6", "9", "11" };

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        dialog = new Dialog(this);
        dialog.setTitle("请选择日期与时间");
        // 找到dialog的布局文件
        View view = getLayoutInflater().inflate(R.layout.time_layout, null);

        // 年
        final WheelTimeView wv_year = (WheelTimeView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
        wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

        // 月
        final WheelTimeView wv_month = (WheelTimeView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCyclic(true);
		wv_month.setLabel("月");
        wv_month.setCurrentItem(month);

        // 日
        final WheelTimeView wv_day = (WheelTimeView) view.findViewById(R.id.day);
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
		wv_day.setLabel("日");
        wv_day.setCurrentItem(day - 1);

        // 时
        final WheelTimeView wv_hours = (WheelTimeView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setCyclic(true);
        wv_hours.setLabel("时");
        wv_hours.setCurrentItem(hour);

        // 分
        final WheelTimeView wv_mins = (WheelTimeView) view.findViewById(R.id.mins);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
        wv_mins.setCyclic(true);
        wv_mins.setLabel("分");
        wv_mins.setCurrentItem(minute);

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelTimeView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String
                        .valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0)
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    else
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelTimeView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    else
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        };
        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小
        int textSize = 0;

        textSize = 30;

        wv_day.TEXT_SIZE = textSize;
        wv_hours.TEXT_SIZE = textSize;
        wv_mins.TEXT_SIZE = textSize;
        wv_month.TEXT_SIZE = textSize;
        wv_year.TEXT_SIZE = textSize;

        Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
        Button btn_cancel = (Button) view
                .findViewById(R.id.btn_datetime_cancel);
        // 确定
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 如果是个数,则显示为"02"的样式
                String parten = "00";
                DecimalFormat decimal = new DecimalFormat(parten);
                // 设置日期的显示
                // tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
                // + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
                // + decimal.format((wv_day.getCurrentItem() + 1)) + " "
                // + decimal.format(wv_hours.getCurrentItem()) + ":"
                // + decimal.format(wv_mins.getCurrentItem()));
                if (typeBt==1){
                    strSTime = (wv_year.getCurrentItem() + START_YEAR) + "-"
                            + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
                            + decimal.format((wv_day.getCurrentItem() + 1)) + " "
                            + decimal.format(wv_hours.getCurrentItem()) + ":"
                            + decimal.format(wv_mins.getCurrentItem());
                    sTime.setText(strSTime);
                }else {
                    strETime = (wv_year.getCurrentItem() + START_YEAR) + "-"
                            + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
                            + decimal.format((wv_day.getCurrentItem() + 1)) + " "
                            + decimal.format(wv_hours.getCurrentItem()) + ":"
                            + decimal.format(wv_mins.getCurrentItem());
                    eTime.setText(strETime);
                }

                dialog.dismiss();
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        // 设置dialog的布局,并显示
        dialog.setContentView(view);
        dialog.show();
    }
}
