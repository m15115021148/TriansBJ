package com.sitemap.beijingtrain.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sitemap.beijingtrain.R;
import com.sitemap.beijingtrain.application.MyApplication;
import com.sitemap.beijingtrain.config.RequestCode;
import com.sitemap.beijingtrain.config.WebUrlConfig;
import com.sitemap.beijingtrain.http.HttpUtil;
import com.sitemap.beijingtrain.model.LoginModel;
import com.sitemap.beijingtrain.util.TelephoneUtil;
import com.sitemap.beijingtrain.util.ToastUtil;
import com.sitemap.beijingtrain.view.HorizontalProgressView;
import com.sitemap.beijingtrain.view.RoundProgressDialog;

/**
 * @author created by chenmeng on 2016/11/14
 */
public class SplashActivity extends BaseActivity implements Runnable {
    private SplashActivity mContext;//本类
    private HttpUtil http;//网络请求
    private RoundProgressDialog progressDialog;//加载条
    private PackageManager pm;//获得PackageManager对象
    private TextView appVersion;//版本号
    private TelephoneUtil telUtil;//手机工具包
    private ImageView mBg;//背景图片
    private Bitmap bt = null;//加载图片类
    private HorizontalProgressView progress;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        appVersion = (TextView) findViewById(R.id.app_version);
        mBg = (ImageView) findViewById(R.id.splash_bg);
        if (bt == null)
            bt = BitmapFactory.decodeResource(getResources(),R.drawable.splash_bg1);
        mBg.setImageBitmap(bt);
        pm = getPackageManager();
        progress=(HorizontalProgressView)findViewById(R.id.progress);
        progress.start();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        appVersion.setText("V" + getVersion());
        if (http == null) {
            http = new HttpUtil(handler);
        }
        if (telUtil == null) {
            telUtil = new TelephoneUtil(this);
        }
        handler.postDelayed(this, 3000);
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
                    if (msg.arg1 == RequestCode.LOGIN) {
                        LoginModel loginModel = JSON.parseObject(msg.obj.toString(), LoginModel.class);
                        if (loginModel != null) {
                            Intent intent = new Intent();
                            if (loginModel.getResult().equals("1")) {//成功
                                intent.setClass(mContext, BottomActivity.class);
                            } else {
                                intent.setClass(mContext, LoginActivity.class);
                                intent.putExtra("imei", telUtil.TELEPHONE_IMEI);
                            }
                            startActivity(intent);
                            mContext.finish();
                        }
                    }
                    break;
                case HttpUtil.FAILURE:
                    ToastUtil.showBottomShort(mContext, RequestCode.ERRORINFO);
                    break;
                case 1:
//                    getLoginMsg(MyApplication.getMac());
                    getLoginMsg(telUtil.TELEPHONE_IMEI);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取登录信息
     *
     * @param mac
     */
    private void getLoginMsg(String mac) {
        if (MyApplication.getNetObject().isNetConnected()) {
            progressDialog = RoundProgressDialog.createDialog(mContext);
            if (progressDialog != null && !progressDialog.isShowing()) {
//                progressDialog.setMessage("登录中...");
//                progressDialog.show();
            }
            http.sendGet(RequestCode.LOGIN, WebUrlConfig.login(mac));
        } else {
            ToastUtil.showBottomShort(mContext, RequestCode.NOLOGIN);
        }
    }

    /**
     * 改写物理按键——返回的逻辑
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handler.removeCallbacks(this);
            mContext.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
        if (bt != null) {
            //释放图片内存
            bt.recycle();
            bt = null;
        }
        //强制回收
        System.gc();
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(1);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
