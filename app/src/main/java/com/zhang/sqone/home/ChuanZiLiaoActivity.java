package com.zhang.sqone.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.SDCARD123Activity;
import com.zhang.sqone.adapter.CommonAdapter;
import com.zhang.sqone.adapter.ViewHolder;
import com.zhang.sqone.bean.Index;
import com.zhang.sqone.bean.Meetingdiscuss;
import com.zhang.sqone.bean.Outbox;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.bean.WenJianBean;
import com.zhang.sqone.utils.GsonUtils;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;
import com.zhang.sqone.views.ListViewForScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * 传资料使用的界面
 *
 * @author ZJP
 *         created at 2016/5/30 15:34
 */
public class ChuanZiLiaoActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    @Bind(R.id.zhoujianren_text)
    TextView zhoujianrenText;
    @Bind(R.id.iamge_tianjia)
    ImageView iamgeTianjia;
    @Bind(R.id.zhuti_text)
    EditText zhutiText;
    @Bind(R.id.zhengwen_text)
    EditText zhengwenText;
    @Bind(R.id.fujian_text)
    TextView fujianText;
    @Bind(R.id.zhuanfa_quxiao)
    LinearLayout zhuanfaQuxiao;
    @Bind(R.id.zhuanfa_zhuanfa)
    LinearLayout zhuanfaZhuanfa;
    @Bind(R.id.tiaojia_iamge)
    ImageView tiaojiaIamge;
    @Bind(R.id.sq_list)
    ListViewForScrollView sqList;
    @Bind(R.id.tiaojia_guanliqi)
    ImageView tiaojiaGuanliqi;
    private Bundle bundle2;
    private String strFromAct2 = "";
    private String ryid = "";
    private String id = "";
    private Intent intent;


    private String zhang;
    private RequestParams params;
    private List<String> fileList = new ArrayList<String>();
    private boolean isone = true;
    private CommonAdapter<String> resultAdapter;
    private List<String> fileListid = new ArrayList<>();
    private Dialog downLoadDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_chuan_zi_liao);
        ButterKnife.bind(this);
        iamgeTianjia.setOnClickListener(this);
        zhuanfaZhuanfa.setOnClickListener(this);
        zhuanfaQuxiao.setOnClickListener(this);
        tiaojiaIamge.setOnClickListener(this);
        zhengwenText.setOnTouchListener(this);
        tiaojiaGuanliqi.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iamge_tianjia:
                Intent intent1 = new Intent();
                intent1.putExtra("bzf", "1");
                intent1.setClass(ChuanZiLiaoActivity.this, TxlActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.zhuanfa_zhuanfa:
                Outbox.ReqOutBox.czlmap.Builder zf = Outbox.ReqOutBox.czlmap.newBuilder();
//                zf.setId(id);
                if (zhutiText.getText().toString().trim().equals("") || fileList.size() == 0 || ryid.equals("") || strFromAct2.equals("")) {
                    Toast.makeText(this, "请完整填写信息", Toast.LENGTH_SHORT).show();
                } else {
                    zf.setTheme(zhutiText.getText().toString().trim());
                    zf.setCon(zhengwenText.getText().toString().trim());
                    zf.setSjrcode(ryid);
                    zf.setSjrname(strFromAct2);
                    StringBuffer s = new StringBuffer();
                    for (int j = 0; j < fileListid.size(); j++) {
                        s.append(fileListid.get(j) + ",");
                    }
                    zf.setAccessory(s.substring(0, s.length() - 1).toString());
                    Log.i("zhang", "文件id++" + s.substring(0, s.length() - 1).toString());
                    final Outbox.ReqOutBox index = Outbox.ReqOutBox.newBuilder().setSid(User.sid).setCzl(zf).setAc("CZL").build();
                    new UniversalHttp() {
                        @Override
                        public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                            index.writeTo(outputStream);
                        }

                        @Override
                        public <T> void inPutInterface(InputStream inputStream) throws IOException {
                            Meetingdiscuss.ReqMeetingDiscuss index = Meetingdiscuss.ReqMeetingDiscuss.parseFrom(inputStream);
                            Log.i("请求响应", "stu" + index.getStu()+"______"+
                                    "scd" + index.getScd()+"______"+
                                    "mag" + index.getMsg()
                            );
                            if (index.getStu() == null || !(index.getStu().equals("1"))) {
                                Toast.makeText(Globals.context,
                                        Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                            }else{
                                if (index.getScd().equals("1")) {
                                    Toast.makeText(ChuanZiLiaoActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                                    initLogin();

                                } else {
                                    Toast.makeText(ChuanZiLiaoActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }.protocolBuffer(ChuanZiLiaoActivity.this, Globals.ZL_URI, null);
                }
                break;
            case R.id.zhuanfa_quxiao:
                finish();
                break;
            case R.id.tiaojia_iamge:
                showFileChooser();
                break;
            case R.id.tiaojia_guanliqi:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                            1);
                } catch (ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    private void showFileChooser() {
        intent = new Intent(ChuanZiLiaoActivity.this, SDCARD123Activity.class);
        intent.putExtra("bsf", "sczl");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException ex) {
        }
    }
    public void initLogin() {
        Log.i("zhang", "我的用户名："+User.sid);
        Index.ReqIndex.Login.Builder login = Index.ReqIndex.Login.newBuilder();
        login.setUsername(User.sid);
        //添加密码
        login.setPassword(User.pwd);
        login.setCid(User.cid);
        login.setType(User.type);
        login.setPhncode(User.mis_id);
        //Index.ReqIndex.ReqRec.Builder message = Index.ReqIndex.ReqRec.newBuilder();
        //message.setPhone("15931295549");
        //message.setPwd("15910438651");
        //message.setYzm("123456");
        final Index.ReqIndex index = Index.ReqIndex.newBuilder().setLogin(login).setAc("LOGIN").build();
        new UniversalHttp() {
            @Override
            public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                index.writeTo(outputStream);
            }

            @Override
            public <T> void inPutInterface(InputStream inputStream) throws IOException {
                Index.ReqIndex jhon1 = Index.ReqIndex.parseFrom(inputStream);
                Log.i("请求响应", "stu" + index.getStu()+"______"+
                        "scd" + index.getScd()+"______"+
                        "mag" + index.getMsg()
                );
                if (index.getStu() == null || !(index.getStu().equals("1"))) {
                    Toast.makeText(Globals.context,
                            Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                }else{
                    if(index.getScd().equals("1")){
                        //头像地址
                        User.IconPath = index.getLogin().getPh();
                        User.wcjd=index.getLogin().getWccd();
                        User.tzts=index.getLogin().getTzts();
                        User.dbts=index.getLogin().getDbts();
                        User.sfzl=index.getLogin().getZlts();
                        User.xxzx = index.getLogin().getXxzxts();
                        Log.i("zhang", "完成度"+User.wcjd);
                        Log.i("zhang", "未读通知"+User.tzts);
                        Log.i("zhang", "未读工作"+User.dbts);
                        Log.i("zhang", "资料条数"+User.sfzl);
                        Log.i("zhang","消息中心"+User.xxzx);
                        finish();
                    }else {
                        Toast.makeText(ChuanZiLiaoActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }.protocolBuffer(ChuanZiLiaoActivity.this, Globals.WS_URI, null);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("ht", "有返回数据");
        if (0 == requestCode) {
            if (!(data == null)) {
                bundle2 = data.getBundleExtra("bundle2");
                strFromAct2 = bundle2.getString("strResult");
                ryid = bundle2.getString("strResult2");
                zhoujianrenText.setText(strFromAct2);
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String url = getRealFilePath(this, uri);
            Log.i("ht", "url----" + getRealFilePath(this, uri));
//            String filename = url.substring(url.lastIndexOf('/') + 1);
            if (url == null) {
                Toast.makeText(this, "请选择文件管理中的文件", Toast.LENGTH_SHORT).show();
            } else {
                zhang = url;
                AsyncHttpClient client = new AsyncHttpClient();
                params = new RequestParams();
                Log.i("sid", User.sid);
                String req = "{\"Ac\":\"TPSC\",\"Para\":{\"SId\":\""
                        + User.sid + "\",\"T\":\"1\",\"Wid\":\"0\"}}";
                params.put("id", req);

                File imgFile = new File(url);
                String filename = imgFile.getName();
                fileList.add(filename);
                if (isone) {
                    resultAdapter = new CommonAdapter<String>(ChuanZiLiaoActivity.this, fileList, R.layout.fayoujian_litem) {
                        @Override
                        public void convert(final ViewHolder holder, final String fileMap) {
                            String end = fileMap.substring(fileMap.lastIndexOf(".") + 1, fileMap.length()).toLowerCase();
                            if (end.equals("doc") || end.equals("docx")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.word);

                            } else if (end.equals("xls") || end.equals("xlsx")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.excel);
                            } else if (end.equals("png")||end.equals("jpg")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.png);
                            } else if (end.equals("txt")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.txt);
                            } else if (end.equals("pdf")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.pdf);
                            } else if (end.equals("ppt") || end.equals("pptx")) {
                                holder.setImageResource(R.id.fj_wenjian, R.mipmap.ppt);
                            }
                            holder.setText(R.id.fj_mingzi, fileMap);
                            holder.setOnClickListener(R.id.fj_chanchu, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    fileList.remove(holder.getPosition());
                                    loadData(fileListid.get(holder.getPosition()));
                                    fileListid.remove(holder.getPosition());
                                    resultAdapter.setData(fileList);
                                    resultAdapter.notifyDataSetChanged();

                                }
                            });

                        }
                    };
                    sqList.setAdapter(resultAdapter);
                    isone = false;
                } else {
                    resultAdapter.setData(fileList);
                    resultAdapter.notifyDataSetChanged();
                }

                if (imgFile.exists() && imgFile.length() > 0) {
                    try {
                        params.put("Zhang.png", imgFile);
                        showDownloadDialog(1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    client.post(ChuanZiLiaoActivity.this, Globals.WS_CZL_WJ, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                        StringBuffer s = new StringBuffer();
//                        for (int j = 0; j<bytes.length-1;j++){
//                            Log.i("swww1", "++"+j+"++" +(char)bytes[j]);
//                            s.append((char)bytes[j]);
//                        }
                            String s = new String(bytes);
                            downLoadDialog.dismiss();
                            WenJianBean s1 = GsonUtils.json2bean(s.toString(),
                                    WenJianBean.class);
                            if (s1 == null || !(s1.getStu() == 1)) {
                                Toast.makeText(ChuanZiLiaoActivity.this, Globals.SER_ERROR,
                                        Toast.LENGTH_SHORT).show();
                                fileListid.add("-1");
                            } else {
                                String ss = "sssss";
                                Log.i("swww1", "成功---" + s1.getRst().getPo() + ss);
                                fileListid.add(s1.getRst().getPo());
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i1 = 0; i1 < fileListid.size(); i1++) {
                                    stringBuilder.append(fileListid.get(i1));
                                }
                            }

                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Log.i("swww2", throwable.getMessage());
                            Log.i("swww3", throwable.toString());
                            Toast.makeText(ChuanZiLiaoActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(long bytesWritten, long totalSize) {
                            super.onProgress(bytesWritten, totalSize);

                            int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                            progressBar.setProgress(count);
                            Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            // TODO Auto-generated method stub
                            super.onRetry(retryNo);
                            // 返回重试次数
                        }
                    });
                } else {
                    Toast.makeText(ChuanZiLiaoActivity.this, "文件不存在", Toast.LENGTH_LONG).show();
                }

            }


        }

    }

    private void loadData(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        params = new RequestParams();
        Log.i("sid", User.sid);
        String req = "{\"Ac\":\"TPSC\",\"Para\":{\"SId\":\""
                + User.sid + "\",\"T\":\"2\",\"Wid\":\"" + id + "\"}}";
        params.put("id", req);

        File imgFile = new File(zhang);
        if (imgFile.exists() && imgFile.length() > 0) {
            try {
                params.put("Zhang.png", imgFile);
                showDownloadDialog(2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post(ChuanZiLiaoActivity.this, Globals.WS_CZL_WJ, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                        StringBuffer s = new StringBuffer();
//                        for (int j = 0; j<bytes.length-1;j++){
//                            Log.i("swww1", "++"+j+"++" +(char)bytes[j]);
//                            s.append((char)bytes[j]);
//                        }
                    String s = new String(bytes);
                    downLoadDialog.dismiss();
                    WenJianBean s1 = GsonUtils.json2bean(s.toString(),
                            WenJianBean.class);
                    if (s1 == null || !(s1.getStu() == 1)) {
                        Toast.makeText(ChuanZiLiaoActivity.this, Globals.SER_ERROR,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String ss = "sssss";
                        Log.i("swww1", "成功---" + s1.getRst().getPo() + ss);
//                            qingjiaH5.loadUrl("javascript:fujian('" + s1.getRst().getPo() + "')");
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.i("swww2", throwable.getMessage());
                    Log.i("swww3", throwable.toString());
                    Toast.makeText(ChuanZiLiaoActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            Toast.makeText(ChuanZiLiaoActivity.this, "文件不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 下载的提示框
     */
    protected void showDownloadDialog(int i) {
        {
            // 构造软件下载对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (i == 1) {
                builder.setTitle("正在上传");
            } else {
                builder.setTitle("正在删除");
            }

            builder.setCancelable(false);
            // 给下载对话框增加进度条
            final LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.downloaddialog, null);
            progressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
            builder.setView(v);
            downLoadDialog = builder.create();
            downLoadDialog.show();

        }

    }

    //将uri转换成String形式
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景
    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        // 解决scrollView中嵌套EditText导致不能上下滑动的问题
        v.getParent().requestDisallowInterceptTouchEvent(true);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                v.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }
}
