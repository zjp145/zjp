package com.zhang.sqone.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.zhang.sqone.Globals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/7.
 */
public abstract class
UniversalHttp {

    /**
     * 创建的使用加载的提示框
     */
    public AlertUtil au;

    /**
     * 调用方法执行操作 访问接口获得数据
     *
     * @param context 上下文对象
     * @param url     文件的下载地址
     * @param ob      控件
     */
    public <T> void protocolBuffer(final Context context,
                                   final String url, final Object ob) {
        /**提示*/
        final Handler myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(Globals.context, "当前网络状态不好", Toast.LENGTH_SHORT).show();
                        break;

                }
                super.handleMessage(msg);
            }
        };
        //创建加载的对话框
        if (ob == null) {
            if (context != null) {
                au = new AlertUtil(context);
            }
        }

        Observable.create(new Observable.OnSubscribe<InputStream>() {
            @Override
            public void call(final Subscriber<? super InputStream> subscriber) {
                //创建子线程加载数据

                HttpURLConnection httpURLConnection;
                try {
                    //建立链接
                    URL gatewayUrl = new URL(url);
                    httpURLConnection = (HttpURLConnection) gatewayUrl.openConnection();

                    //设置连接属性
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    /**设置时间*/
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
                    //byte[] requestStringBytes = requestString.getBytes(ENCODING_REQUEST);

                    //设置请求属性
                    //httpURLConnection.setRequestProperty("Content-length", "" + requestStringBytes.length);
                    httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");

                    //建立输出流，并写入数据
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    try {
                    //  这是一个回调接口将我们要传入的数据信息添加头输出流中
                        outPutInterface(outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //  关闭流
                    outputStream.close();
                    //获得请求结果码
                    int responseCode = httpURLConnection.getResponseCode();
                    if (HttpURLConnection.HTTP_OK == responseCode) {
                        Log.i("zhang", "访问成功");
                        //请求访问成功获得服务端的输入流（数据）
                        InputStream inputStream = httpURLConnection.getInputStream();
                        if (au != null) {
                            // 关闭输入框
                            au.closeDialog();
                        }

                        if (inputStream != null) {
                            //  子线程加载数据
                            subscriber.onNext(inputStream);

                        }

                    } else {
                        // 请求访问失败
                        if (au != null) {
                            //   关闭输入框
                            au.closeDialog();

                        }
                        if (AppUtil.networkCheck() == false) {
                        //  如果没有网络
                            Toast.makeText(Globals.context, "没有网络", Toast.LENGTH_SHORT).show();
                        } else {
                        //   发生错误的时候提示
                            Toast.makeText(Globals.context, Globals.SER_ERROR,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    //  当请求超过设置的请求时间的是好 提示网络不好
                    au.closeDialog();
                    /**传递消息*/
                    Message message = new Message();
                    message.what = 1;
                    myHandler.sendMessage(message);
                    e.printStackTrace();
                }
                //  rxJava使用的的方法 子线程运行中可以操作
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        try {
                            //使用回调方法  将我们获得的数据回调出去
                            inPutInterface(inputStream);
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //    回调接口方法
    public abstract <T> void outPutInterface(OutputStream outputStream) throws IOException;

    public abstract <T> void inPutInterface(InputStream inputStream) throws IOException;

}
