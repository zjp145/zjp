package com.zhang.sqone.ceshi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhang.sqone.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HttpasyncActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 创建一个简单的get请求
     */
    @Bind(R.id.ceshi1)
    Button ceshi1;
    /**
     * 简单的post请求
     */
    @Bind(R.id.ceshi2)
    Button ceshi2;
    /**
     * 文件流上传
     */
    @Bind(R.id.ceshi3)
    Button ceshi3;
    /**文件下载*/
    @Bind(R.id.ceshi4)
    Button ceshi4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpasync);
        ButterKnife.bind(this);
        ceshi1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ceshi1:
                //创建一个请求对象
                AsyncHttpClient client = new AsyncHttpClient();

                //使用get方法发送一个请求 参数是网络请求的地址，和成功失败的回调
                client.get("http://baidu.com/", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        //bytes 是访问成功返回的数据 打印返回结果
                        String info = new String(bytes);
                        System.out.print(info);

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        //如果失败 打印访问失败的数据
                        String info = new String(bytes);
                        System.out.print(info);
                    }
                });
                break;
            case R.id.ceshi2:
                //创建一个请求对象
                AsyncHttpClient client1 = new AsyncHttpClient();
                //设置参数
                RequestParams params = new RequestParams();
                params.put("参数的key", "参数的value值");
                //创建post请求
                client1.post("post请求的网络地址", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
                break;
            case R.id.ceshi3:
                //创建一个请求对象
                AsyncHttpClient client2 = new AsyncHttpClient();
                //在设置参数的时候 可以添加一个流对象
                RequestParams params1 = new RequestParams();
                //添加一个数组在io流中
//                ByteArrayInputStream in = new ByteArrayInputStream("jkjk".getBytes());
//                params1.put("key",in,"文件名字");

//                创建一个文件流
//                try {
//                    FileInputStream in = new FileInputStream("文件路径");
//                    params1.put("key",in,"文件名字");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }


                //直接将文件传输
                //创建一个文件的路径
                String path = "文件的路径";
                try {
                    params1.put("key", new File(path), "文件传输类型");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                client2.post(this, "网络地址", params1, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });

                break;
            case R.id.ceshi4:
                AsyncHttpClient client3 = new AsyncHttpClient();
                client3.get("下载地址", new FileAsyncHttpResponseHandler(this) {
                    //失败
                    @Override
                    public void onFailure(int i, Header[] headers, Throwable throwable, File file) {

                    }
                    //成功
                    @Override
                    public void onSuccess(int i, Header[] headers, File file) {
                        //打印存储路径
                        System.out.print(file.getAbsolutePath());
                        //创建建一个你要存储的路径
                        String path = "路径";
                        try {
                            //获得文件流
                            InputStream inputStream = new FileInputStream(file);
                            //创建文件输出流
                            OutputStream outputStream = new FileOutputStream(path);
                            int len = -1;
                            byte[] b = new byte[1024];
                            while((len=inputStream.read(b))!=-1){
                                outputStream.write(b,0,len);
                                outputStream.flush();
                            }
                            inputStream.close();
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

        }
    }
}
