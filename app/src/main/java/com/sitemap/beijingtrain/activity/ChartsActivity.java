package com.sitemap.beijingtrain.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.animation.AnimationEasing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.model.LineChartModel;
import com.sitemap.beijingtrain.model.PieChartModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 数据统计图
 * @author chenmeng
 */
public class ChartsActivity extends BaseActivity implements View.OnClickListener{
    private ChartsActivity mContext;//本类
    private LinearLayout back;//返回上一层
    private PieChart mPieChart;//饼形图
    private Typeface mTf;//字体样式
    private String[] mParties = new String[] {
            "正常", "故障", "告警"
    };
    private int[] line_color = new int[]{
            Color.rgb(71, 204, 255), Color.rgb(253, 203, 76), Color.rgb(255, 0, 0),
    };
    private List<PieChartModel> pieList;//饼形图数据
    private LineChart mLineChart;//线形图
    private List<LineChartModel> lineList;//线形图数据
    private TextView tvPie;//显示的百分比 饼形图
    private String sTime,eTime,time;//开始时间，终止时间
    private TextView chartDate;//时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        mContext = this;
        back = (LinearLayout) findViewById(R.id.chart_back);
        back.setOnClickListener(this);
        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        chartDate = (TextView) findViewById(R.id.chart_date);
        tvPie = (TextView) findViewById(R.id.pie_result);

        String pieChart = getIntent().getStringExtra("pieChart");
        String lineChart = getIntent().getStringExtra("lineChart");
        sTime = getIntent().getStringExtra("s_time");
        eTime = getIntent().getStringExtra("e_time");
        chartDate.setText("( "+sTime+" - "+eTime+" )");
        time = sTime.split(" ")[0]+"\n|\n"+eTime.split(" ")[0];
        pieList = JSON.parseArray(pieChart, PieChartModel.class);
        lineList = JSON.parseArray(lineChart, LineChartModel.class);
        if (pieList!=null){
            initPieChartData(pieList);
        }
        if(lineList!=null){
            initLineChartData(lineList);
        }

    }

    /**
     * 初始化饼形图
     */
    private void initPieChartData(List<PieChartModel> list){
        mPieChart.setUsePercentValues(true);
        mPieChart.setHoleColorTransparent(true);
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mPieChart.setHoleRadius(60f);
//        mPieChart.setHoleColor(line_color[0]);
        mPieChart.setDescription("");
        mPieChart.setDrawCenterText(true);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setCenterText("饼状图");//中间不显示文本
        mPieChart.setCenterTextSize(12f);
        mPieChart.setCenterTextColor(Color.WHITE);

        mPieChart.setDrawSliceText(false);//不显示文字
        //设置数据
        setPieData(list);

        mPieChart.animateXY(1500, 1500, AnimationEasing.EasingOption.EaseOutBack);
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setFormSize(10f);
        l.setTextSize(10f);
        l.setTextColor(Color.WHITE);
        l.setLabels(mParties);

        mPieChart.invalidate();
    }

    /**
     * 初始化线形图的数据
     * @param list
     */
    private void initLineChartData(List<LineChartModel> list){
        // apply styling
        mLineChart.setDescription("");
        mLineChart.setDrawGridBackground(false);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(8f);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5);
        leftAxis.setStartAtZero(true);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(2);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        // set data
        mLineChart.setData(setLineData(list));
        mLineChart.animateX(2000);

        Legend l = mLineChart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setTextSize(10f);
        // do not forget to refresh the chart
        mLineChart.invalidate();

    }

    @Override
    public void onClick(View v) {
        if(v == back){
            finish();
        }
    }

    /**
     * 饼形图数据模式
     */
    private void setPieData(List<PieChartModel> list) {
        String result = "";//百分比
        int sum = 0;//总数据
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            sum = sum + Integer.parseInt(list.get(i).getCount());
        }
        Log.i("jack", "sum=" + sum);
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);
        for (int i = 0; i < list.size(); i++) {
            yVals1.add(new Entry((float)(Integer.parseInt(list.get(i).getCount()))/sum*100,i));
            result = result + format.format((float)(Integer.parseInt(list.get(i).getCount()))/sum) + "  ";
        }
        tvPie.setText(result);
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : line_color)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setDrawValues(false);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTf);
        mPieChart.setData(data);
        // undo all highlights
        mPieChart.highlightValues(null);

    }

    /**
     *设置线性图的数据
     * @return
     */
    private LineData setLineData(List<LineChartModel> list) {
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            e1.add(new Entry(Integer.parseInt(list.get(i).getnNum()),i));
        }
        LineDataSet d1 = new LineDataSet(e1, "正常");
        d1.setLineWidth(2f);
        d1.setCircleSize(2f);
        d1.setColor(line_color[0]);
        d1.setHighLightColor(line_color[0]);
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            e2.add(new Entry(Integer.parseInt(list.get(i).geteNum()),i));
        }
        LineDataSet d2 = new LineDataSet(e2, "故障");
        d2.setLineWidth(2f);
        d2.setCircleSize(2f);
//        d2.setDrawCircles(false);
        d2.setCircleColor(line_color[1]);
        d2.setColor(line_color[1]);
        d2.setHighLightColor(line_color[1]);
        d2.setDrawValues(false);

        ArrayList<Entry> e3 = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            e3.add(new Entry(Integer.parseInt(list.get(i).getwNum()),i));
        }
        LineDataSet d3 = new LineDataSet(e3, "告警");
        d3.setLineWidth(2f);
        d3.setCircleSize(2f);
//        d3.setDrawCircles(false);
        d3.setCircleColor(line_color[2]);
        d3.setColor(line_color[2]);
        d3.setHighLightColor(line_color[2]);
        d3.setDrawValues(false);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
//        sets.add(d1);
        sets.add(d2);
        sets.add(d3);

        LineData cd = new LineData(getTimes(list), sets);
        return cd;
    }

    /**
     * 得到时间点数据
     * @return
     */
    private ArrayList<String> getTimes(List<LineChartModel> list) {
        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            m.add(lineList.get(i).getTime().replace("-","."));
        }
        m.add("");
        return m;
    }

}
