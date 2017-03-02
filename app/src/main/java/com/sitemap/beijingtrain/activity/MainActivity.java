package com.sitemap.beijingtrain.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.adapter.RecyclerViewAdapter;
import com.sitemap.beijingtrain.config.RequestCode;
import com.sitemap.beijingtrain.http.HttpUtil;
import com.sitemap.beijingtrain.model.TimeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 无用
 */
public class MainActivity extends BaseActivity {
    private RecyclerView rvContainer;
    private int mWidth, offX;//横向移动
    private int width = 0;//item的宽度
    private String startTime = "2016-10-18 00:00";//起始时间
    private String endTime = "2016-10-20 00:00";//结束时间
    private int addTime = 10 * 60 * 1000;//每次增加时间
    private Long stime, etime;//起始结束时间的数值
    private List<TimeModel> lTimeModel = new ArrayList<TimeModel>();//数据集合
    private RecyclerViewAdapter adapter = null;//adapter
    private int index = 2;//绿色的起始下标
    private final int CHANGE = 0X0010;//改变绿色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvContainer = (RecyclerView) findViewById(R.id.rvContainer);
        WindowManager wm = this.getWindowManager();

        width = wm.getDefaultDisplay().getWidth();
        Log.i("TAG", width + "");
        width = width / 5;
//        int height = wm.getDefaultDisplay().getHeight();

        initTimeList();


        adapter = new RecyclerViewAdapter(this, lTimeModel);
        rvContainer.setAdapter(adapter);
        LinearLayoutManager ll = new LinearLayoutManager(this);//这个是必须的，是设置是横向还是竖向滑动
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);//横向
        rvContainer.setLayoutManager(ll);
        rvContainer.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {//这个是状态监听
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:// 停止滑动状态
                        int scrollX = offX % width;
                        int scroll = 0;
                        if (scrollX > width / 2) {//滑动超过itemde一半,向右滑动
                            scroll = width - scrollX;
                        } else {//滑动没有超过itemde一半,向左滑动，返回上一个值
                            scroll = -scrollX;
                        }
                        rvContainer.smoothScrollBy(scroll, 0);//执行滑动操作
                        Log.i("TAG", "scroll:" + scroll + " diff:" + scrollX + "  offX:" + offX);
                        if (scroll == 0 && scrollX == 0) {
                            new Handler().postDelayed(new Runnable() {

                                public void run() {
                                    if (offX % width == 0) {
                                        lTimeModel.get(index).setType(0);
                                        index = 2 + offX / width;
                                        lTimeModel.get(index).setType(1);
                                        handler.sendEmptyMessage(CHANGE);
                                    }

                                }
                            }, 100);
                        }
//                        recyclerView.get

                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://开始滑动状态

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {//这个回调很重要，这个dx,dy 表示的是偏移量，把所有偏移量累加即可得到滑动的总距离，注意值有正负表示左右滑动
//                L.d(L.TAG_TEST, "x:" + dx + "  y:" + dy);
                offX += dx;

            }
        });

        rvContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        for (int i = 0; i < lTimeModel.size(); i++) {
//                            lTimeModel.get(i).setType(0);
//                        }
                        adapter.notifyDataSetChanged();
//                        new Handler().postDelayed(new Runnable() {
//
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }, 100);
//                        new Handler().postDelayed(new Runnable() {
//
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }, 100);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    if (msg.arg1 == RequestCode.QUERYTEMPERATURE) {
                    }
                    if (msg.arg1 == RequestCode.GETALLTRAIN) {
                    }
                    break;
                case HttpUtil.FAILURE:

                    break;
                case CHANGE:
                    Log.i("TAG", index + "");

                    adapter.notifyDataSetChanged();

                    break;
                default:
            }
        }
    };

    /**
     * 初始化时间列表
     */
    private void initTimeList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
            stime = start.getTime();
            etime = end.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lTimeModel.clear();
        TimeModel tm0 = new TimeModel();
        tm0.setTime("");
        tm0.setType(0);
        lTimeModel.add(tm0);
        lTimeModel.add(tm0);
        for (; stime <= etime; stime = stime + addTime) {
            TimeModel tm = new TimeModel();
            tm.setTime(sdf.format(stime));
            tm.setType(0);
            lTimeModel.add(tm);
        }
        lTimeModel.add(tm0);
        lTimeModel.add(tm0);

        for (int i = 0; i < lTimeModel.size(); i++) {
            if (i == index) {
                lTimeModel.get(i).setType(1);
            }
        }
    }
}
