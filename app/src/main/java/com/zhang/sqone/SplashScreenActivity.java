package com.zhang.sqone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhang.sqone.bean.LoginResult;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.my.Dengluactivity;
import com.zhang.sqone.utils.AppUtil;
import com.zhang.sqone.utils.GsonUtils;
import com.zhang.sqone.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/*
* 程序的欢迎的页面如果用户已经登录的情况下不需要登录直接进入社区界面
* */
public class SplashScreenActivity extends BaseActivity {
    //获取本地的轻量级的存储对象
    private SharedPreferences myPrefer;
    //欢迎页面的展示时间
    private final int SPLASH_DISPLAY_LENGHT = 1500;
    //获得的社区id ，和登录的标示符
    private String sqid, Sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wwwwwwwwwwww", "onCreate ");
        setContentView(R.layout.activity_splash_screen);
        myPrefer = getSharedPreferences(Globals.SPLASH_USERTYPE, Activity.MODE_PRIVATE);

        //开启一个线程将本地保存的数据 登录
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //创建的如果本地没有存在数据的情况下跳转到登录的界面上 否则直接使用的本地的数据登录app
                if ("".equals(SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_YD, ""))){
                    SharedPreferencesUtils.saveString(SplashScreenActivity.this,
                            Globals.USER_YD, "1");
                    Intent intent = new Intent(SplashScreenActivity.this,YinDaoYeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if ("".equals(SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_PHONE, ""))
                        && "".equals(SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_PASSWORD, ""))) {
                    Intent intent = new Intent(SplashScreenActivity.this, Dengluactivity.class);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                    Log.i("zhangjianpeng", "本地没有数据");
                } else {
                    Log.i("zhangjianpeng", "本地有数据");
                    //如果有数据的情况下将保存的数据昵称数据放到静态类中
                    User.nc = SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_NC, "");
                    initLogin();
                }
            }
        }, SPLASH_DISPLAY_LENGHT);


    }

    //登录方法
    public void initLogin() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(Globals.WS_POST_KEY, "{\"Ac\":\"Login\",\"Para\":{\"Mb\":\"" + SharedPreferencesUtils.getString(
                SplashScreenActivity.this, Globals.USER_PHONE, null)
                + "\",\"Pwd\":\"" + SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_PASSWORD, null) + "\"}}");
        RequestQueue mRequestQueue = Volley.newRequestQueue(SplashScreenActivity.this);
        Log.i(Globals.LOG_TAG, "paramsss=" + params);
        //创建请求的方式登录
        StringRequest sr = new StringRequest(Request.Method.POST, Globals.WS_URI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(Globals.LOG_TAG, "这里打印 首页是否登陆");
                Log.i(Globals.LOG_TAG, response);
                if (response != null) {
                    LoginResult s = GsonUtils.json2bean(response,
                            LoginResult.class);
                    if (s == null || !(s.Stu == 1)) {
//								Toast.makeText(SplashScreenActivity.this,
//										Globals.SER_ERROR, Globals.TOAST_SHORT)
//										.show();
                    } else if (s.Stu == 1 && s.Rst.Scd == 0) {
//								Toast.makeText(SplashScreenActivity.this,
//										s.Rst.Msg, Globals.TOAST_SHORT).show();
                    } else {
                        Toast.makeText(SplashScreenActivity.this,
                                s.Rst.Msg, Toast.LENGTH_SHORT);
                        sqid = s.Rst.Sqid;
                        Sid = s.Rst.Sid;
                        User.setLoginInfo(SharedPreferencesUtils.getString(SplashScreenActivity.this, Globals.USER_PHONE, null),
                                true, s.Rst.Sid + "", s.Rst.Ph, s.Rst.Sqid, s.Rst.Sh);

                    }
                }
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                mainIntent.putExtra(Globals.SH_ID, sqid);
                mainIntent.putExtra(Globals.PROJECT_ID, Sid);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.i("Response", "error" + error.getMessage());
                Toast.makeText(Globals.context, "您的网络有问题请查证后启动!", Toast.LENGTH_SHORT)
                        .show();
                if (AppUtil.networkCheck() == false) {
                    Toast.makeText(Globals.context, "没有网络", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(sr);
        if (sr.getRetryPolicy().getCurrentTimeout() > 6000) {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    Intent mainIntent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
                }
            }, sr.getRetryPolicy().getCurrentTimeout());
        }
    }


}
