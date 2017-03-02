package com.sitemap.beijingtrain.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.application.MyApplication;
import com.sitemap.beijingtrain.config.RequestCode;
import com.sitemap.beijingtrain.config.WebUrlConfig;
import com.sitemap.beijingtrain.http.HttpUtil;
import com.sitemap.beijingtrain.model.PositionModel;
import com.sitemap.beijingtrain.model.TrackModel;
import com.sitemap.beijingtrain.util.ToastUtil;
import com.sitemap.beijingtrain.view.RoundProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity implements View.OnClickListener {
    private MapActivity mContext;
    private MapView mMapView;
    private BaiduMap mBaiduMap;//百度地图对象
    private BaiduMap.OnMarkerClickListener markClick;// 地图标注点击事件
    private LinearLayout mBack;//返回上一层
    private String checi;//传过来的车次
    private String time;//传过来的时间
    private HttpUtil http;//网络请求
    private RoundProgressDialog progressDialog;//加载 条
    private List<PositionModel> lpositionModel = new ArrayList<PositionModel>();//进来加载的点的集合
    private List<TrackModel> ltrackModel = new ArrayList<TrackModel>();//轨迹的集合
    private LinearLayout map_change_lay,guijilay;//地图切换
    private TextView map_change_img,map_change_txt,guiji;//地图切换的图片、文字，轨迹图标
    private int statu=0;//点标记方法的状态，0为不弹框，1为弹框
    private List<LatLng> points=new ArrayList<LatLng>();//线的点的集合
    private LatLng nowPosition;//最新位置
    private String selSTime,selETime;//开始时间,结束时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = this;
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBack = (LinearLayout) findViewById(R.id.map_back);
        mBack.setOnClickListener(this);
        map_change_lay=(LinearLayout)findViewById(R.id.map_change_lay);
        map_change_lay.setOnClickListener(this);
        map_change_img=(TextView) findViewById(R.id.map_change_img);
        map_change_txt=(TextView) findViewById(R.id.map_change_txt);
        guiji=(TextView) findViewById(R.id.guiji);
        guijilay= (LinearLayout) findViewById(R.id.guijilay);
        guijilay.setOnClickListener(this);
        // 隐藏缩放控件
        hidezoomView();
        mBaiduMap = mMapView.getMap();
        checi = getIntent().getStringExtra("checi");
        time = getIntent().getStringExtra("time");
        selSTime=getIntent().getStringExtra("selSTime");
        selETime=getIntent().getStringExtra("selETime");
//        Log.i("TAG",time);
        initNet();
//        setPoint((new LatLng(39.8346230000, 116.3768670000)),"北京丰台大红门货场！","", 0);
//        setPoint((new LatLng(lat, lng)),"这是点！","", 0);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();// 关闭进度条
            }
            switch (msg.what) {
                case HttpUtil.SUCCESS:
                    if (msg.arg1 == RequestCode.SEARCHPOSITION) {
                    if (msg.obj.toString() != null) {
                        lpositionModel.clear();
                        lpositionModel = JSON.parseArray(msg.obj.toString(), PositionModel.class);
                        if (lpositionModel.size() > 0) {
                            mBaiduMap.clear();
                            for (int i = 0; i < lpositionModel.size(); i++) {
                                if (lpositionModel.get(i).getLat().equals("0") || lpositionModel.get(i).getLng().equals("0")||Double.parseDouble(lpositionModel.get(i).getLat())<0||Double.parseDouble(lpositionModel.get(i).getLng())<0) {
                                    ToastUtil.showBottomShort(mContext, "没有有效位置信息");
                                    return;
                                }
                                setPoint((new LatLng(Double.parseDouble(lpositionModel.get(i).getLat()), Double.parseDouble(lpositionModel.get(i).getLng()))),"当前时间："+lpositionModel.get(i).getTime() , "车次："+checi+"    速度："+lpositionModel.get(i).getSpeed()+"km/h", 1);
                            }
                        }
                    } else {
                        ToastUtil.showBottomShort(mContext, "没有位置信息");
                    }
                }
                    if (msg.arg1 == RequestCode.SEARCHPOSITIONTRACK) {
                        if (msg.obj.toString() != null) {
                            ltrackModel.clear();
                            ltrackModel = JSON.parseArray(msg.obj.toString(), TrackModel.class);
                            Log.i("TAG","ltrackModel:"+ltrackModel.size());
                            if (ltrackModel.size() > 2) {
                                guijilay.setClickable(false);
                                guiji.setBackgroundResource(R.drawable.guijihui);
                                nowPosition=new LatLng(Double.parseDouble(ltrackModel.get(0).getLat()), Double.parseDouble(ltrackModel.get(0).getLng()));
                                setPoint(nowPosition,"当前时间："+ltrackModel.get(0).getTime(),"车次："+checi+"    速度："+ltrackModel.get(0).getSpeed()+"km/h",1);
                                ltrackModel.remove(0);
                                points.clear();
                                for (int i = 0; i < ltrackModel.size(); i++) {
                                    if (ltrackModel.get(i).getLat().equals("0") || ltrackModel.get(i).getLng().equals("0")||Double.parseDouble(ltrackModel.get(i).getLat())<0||Double.parseDouble(ltrackModel.get(i).getLng())<0) {
                                        return;
                                    }
                                    points.add(new LatLng(Double.parseDouble(ltrackModel.get(i).getLat()), Double.parseDouble(ltrackModel.get(i).getLng())));

                                   if (ltrackModel.get(i).getIsStop().equals("1")) {
//                                       mBaiduMap.removeMarkerClickListener(markClick);
                                           Marker marker = null;
                                           // 构建用户绘制点Option对象
                                           // OverlayOptions polygonOption = new DotOptions()
                                           // .color(Integer.valueOf(Color.RED))
                                           // .radius(14).center(points.get(i)).zIndex(i);
                                           // 构建Marker图标
                                           BitmapDescriptor bitmap = BitmapDescriptorFactory
                                                   .fromResource(R.drawable.tingche);
                                           // 构建MarkerOption，用于在地图上添加Marker
                                           OverlayOptions option = new MarkerOptions()
                                                   .position(points.get(i)).icon(bitmap)
                                                   .zIndex(2);
                                           marker = (Marker) mBaiduMap.addOverlay(option);
                                           Bundle bundle = new Bundle();
                                           bundle.putSerializable("info", String.valueOf(i));
                                           marker.setExtraInfo(bundle);
                                       markClick = new BaiduMap.OnMarkerClickListener() {
                                           @Override
                                           public boolean onMarkerClick(Marker arg0) {
                                               if (arg0.getZIndex() == 3) {
                                                   return false;
                                               }
                                               View contentView = getLayoutInflater().inflate(R.layout.mapmark_layout,
                                                       null);
                                               TextView location_address = (TextView) contentView
                                                       .findViewById(R.id.location_address);
                                               TextView location_time = (TextView) contentView
                .findViewById(R.id.location_time);
                                               location_address.setText("停车时间段："+ltrackModel.get(
                                                       Integer.parseInt((String) arg0
                                                               .getExtraInfo().get("info")))
                                                       .getTime());
                                               location_address.setGravity(Gravity.CENTER);
                                               location_time.setText("车次："+checi+"    速度："+ltrackModel.get(
                                                       Integer.parseInt((String) arg0
                                                               .getExtraInfo().get("info"))).getSpeed()+"km/h");
                                               location_time.setGravity(Gravity.CENTER);
                                               contentView
                                                       .setOnClickListener(new View.OnClickListener() {

                                                           @Override
                                                           public void onClick(View v) {
                                                               mBaiduMap.hideInfoWindow();
                                                           }
                                                       });
                                               // 构建对话框用于显示
                                               // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                                               InfoWindow mInfoWindow = new InfoWindow(
                                                       contentView, points.get(Integer
                                                       .parseInt((String) arg0
                                                               .getExtraInfo().get(
                                                                       "info"))), -20);
//                                               Log.i("TAG","zuobiao:"+)
                                               // 显示InfoWindow
                                               mBaiduMap.showInfoWindow(mInfoWindow);
                                               return false;
                                           }
                                       };
//						addArrow(points,20,60);
                                       mBaiduMap.setOnMarkerClickListener(markClick);
//                                       setPoint((new LatLng(Double.parseDouble(ltrackModel.get(i).getLat()), Double.parseDouble(ltrackModel.get(i).getLng()))), ltrackModel.get(i).getTime(), "车次：" + checi, 1);
                                   }
                                }

                                setLine(points);
                                ToastUtil.showBottomShort(mContext, "轨迹加载完成");
                            }else if(ltrackModel.size() >0){
                                guijilay.setClickable(false);
                                guiji.setBackgroundResource(R.drawable.guijihui);
                                ToastUtil.showBottomShort(mContext, "轨迹加载完成");
                            }

                        } else {
                            ToastUtil.showBottomShort(mContext, "没有轨迹信息");
                        }
                    }
                    break;
                case HttpUtil.FAILURE:
                    ToastUtil.showBottomShort(mContext, RequestCode.ERRORINFO);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 隐藏缩放控件
     */
    private void hidezoomView() {
        final int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 初始化网络
     */
    private void initNet() {
        http = new HttpUtil(handler);
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(mContext);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("加载中...");
                progressDialog.show();
            }
            http.sendGet(RequestCode.SEARCHPOSITION, WebUrlConfig.searchPosition(time, checi));
        } else {
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * update地图的状态与变化
     *
     * @param point
     * @param zoom
     */
    private void updateStatus(LatLng point, int zoom) {
        MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(zoom)
                .build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate =

                MapStatusUpdateFactory.newMapStatus(mMapStatus);
        // 改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    /**
     * 别的地图转百度坐标
     *
     * @param ll
     * @return
     */
    private LatLng changeBaidu(LatLng ll) {
        // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
        // sourceLatLng待转换坐标
        converter.coord(ll);
        LatLng desLatLng = converter.convert();
        Log.i("TAG", "latitude:" + desLatLng.latitude + "longitude:" + desLatLng.longitude);
        return desLatLng;
    }

    /**
     * 原始GPS转百度地图
     * @param ll
     * @return
     */
    private LatLng changeBaiduByGPS(LatLng ll) {
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(ll);
        LatLng desLatLng = converter.convert();
        Log.i("TAG", "latitude:" + desLatLng.latitude + "longitude:" + desLatLng.longitude);
        return desLatLng;
    }




    /**
     * 自定义点标记
     *
     * @param point
     * @param adress
     * @param time
     * @param type  0默认点，1带点击事件
     */
    public void setPoint(LatLng point, String adress, String time, int type) {
        mBaiduMap.clear();
        // 创建InfoWindow展示的自定义view,显示详情对话框
        TextView button = setPop(adress);

        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.point);

        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap).zIndex(3);
        View contentView = getLayoutInflater().inflate(R.layout.mapmark_layout,
                null);
        TextView location_time = (TextView) contentView
                .findViewById(R.id.location_time);
        TextView location_address = (TextView) contentView
                .findViewById(R.id.location_address);

        location_address.setText(adress);
        contentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBaiduMap.hideInfoWindow();
            }
        });
        // 构建对话框用于显示
        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        final InfoWindow mInfoWindow = new InfoWindow(contentView, point, -20);

        markClick = new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                mBaiduMap.showInfoWindow(mInfoWindow);
                return false;
            }
        };
        if (type == 1) {
                    location_time.setText(time);
//             显示InfoWindow
//            mBaiduMap.showInfoWindow(mInfoWindow);
            mBaiduMap.setOnMarkerClickListener(markClick);
        }
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        // 正常显示
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        if (type==1){
            // 定义地图状态
            updateStatus(point, 10);
        }else {
            // 定义地图状态
            updateStatus(point, 16);
        }

    }


    /**
     * 自定义地图对话框，展示详情
     *
     * @param adress
     * @return
     */
    private TextView setPop(String adress) {
        TextView button = new TextView(getApplicationContext());
        button.setBackgroundColor(getResources().getColor(R.color.white));
        button.setTextSize(14);
        button.setTextColor(getResources().getColor(R.color.black));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                200, LinearLayout.LayoutParams.MATCH_PARENT);
        button.setText(adress);
        layoutParams.setMargins(50, 0, 50, 0);
        button.setLayoutParams(layoutParams);

        return button;
    }

    /**
     * 画线
     * @param points
     */
    private void setLine(List<LatLng> points){
        if(points==null||points.size()<2){
            return;
        }
        OverlayOptions ooPolyline = new PolylineOptions()
                .width(5)
                .color(Color.parseColor("#ff0000"))
                .points(points).zIndex(1);
        mBaiduMap.addOverlay(ooPolyline);
        updateStatus(points.get(points.size()/2),9);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            finish();
        }
        if (v==map_change_lay){
            if (statu==0){
                statu=1;
                map_change_img.setBackgroundResource(R.drawable.pingmian);
                map_change_txt.setText("2D平面图");
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }else {
                statu=0;
                map_change_img.setBackgroundResource(R.drawable.weixing);
                map_change_txt.setText("卫星图");
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        }
        if (v==guijilay){
            if (MyApplication.getNetObject().isNetConnected()) {
                progressDialog = RoundProgressDialog.createDialog(mContext);
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage("加载中...");
                    progressDialog.show();
                }
                http.sendGet(RequestCode.SEARCHPOSITIONTRACK, WebUrlConfig.searchPositionTrack(selSTime,selETime, checi));

            } else {
                ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
            }
        }
    }
}
