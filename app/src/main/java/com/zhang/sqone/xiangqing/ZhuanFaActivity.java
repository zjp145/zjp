package com.zhang.sqone.xiangqing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.sqone.BaseActivity;
import com.zhang.sqone.Globals;
import com.zhang.sqone.R;
import com.zhang.sqone.bean.Outbox;
import com.zhang.sqone.bean.User;
import com.zhang.sqone.home.TxlActivity;
import com.zhang.sqone.utils.UniversalHttp;
import com.zhang.sqone.utilss.SystemStatusManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 详情的转发界面
 *
 * @author ZJP
 *         created at 2016/5/11 9:47
 */
public class ZhuanFaActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.zhoujianren_text)
    TextView zhoujianrenText;
    @Bind(R.id.iamge_tianjia)
    ImageView iamgeTianjia;
    @Bind(R.id.zhuti_text)
    EditText zhutiText;
    @Bind(R.id.fujian_text)
    TextView fujianText;
    @Bind(R.id.zhengwen_text)
    EditText zhengwenText;
    @Bind(R.id.zhuanfa_quxiao)
    LinearLayout zhuanfaQuxiao;
    @Bind(R.id.zhuanfa_zhuanfa)
    LinearLayout zhuanfaZhuanfa;
    private Bundle bundle2;
    private String strFromAct2="";
    private String ryid="";
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_zhuan_fa);
        ButterKnife.bind(this);
       id =  getIntent().getStringExtra("id");
        zhutiText.setText(getIntent().getStringExtra("zt"));
        zhengwenText.setText(getIntent().getStringExtra("nr"));
        fujianText.setText(getIntent().getStringExtra("wj"));
        iamgeTianjia.setOnClickListener(this);
        zhuanfaZhuanfa.setOnClickListener(this);
        zhuanfaQuxiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iamge_tianjia:
                Intent intent1 = new Intent();
                intent1.putExtra("bzf","1");
                intent1.setClass(ZhuanFaActivity.this, TxlActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.zhuanfa_zhuanfa:
                Outbox.ReqOutBox.zfmap.Builder zf = Outbox.ReqOutBox.zfmap.newBuilder();
                zf.setId(id);
                if(zhutiText.getText().toString().trim().equals("")||zhengwenText.getText().toString().trim().equals("")||ryid.equals("")||strFromAct2.equals("")){
                    Toast.makeText(this,"请完整填写信息",Toast.LENGTH_SHORT).show();
                }else{
                    zf.setTheme(zhutiText.getText().toString().trim());
                    zf.setCon(zhengwenText.getText().toString().trim());
                    zf.setSjrcode(ryid);
                    zf.setSjrname(strFromAct2);
                    final Outbox.ReqOutBox index = Outbox.ReqOutBox.newBuilder().setSid(User.sid).setZf(zf).setAc("ZFSZL").build();
                    new UniversalHttp() {
                        @Override
                        public <T> void outPutInterface(OutputStream outputStream) throws IOException {
                            index.writeTo(outputStream);
                        }

                        @Override
                        public <T> void inPutInterface(InputStream inputStream) throws IOException {
                            Outbox.ReqOutBox index = Outbox.ReqOutBox.parseFrom(inputStream);
                            Log.i("请求响应", "stu" + index.getStu()+"______"+
                                    "scd" + index.getScd()+"______"+
                                    "mag" + index.getMsg()
                            );
                            if (index.getStu() == null || !(index.getStu().equals("1"))) {
                                Toast.makeText(Globals.context,
                                        Globals.SER_ERROR, Toast.LENGTH_SHORT).show();

                            }else{
                                if (index.getScd().equals("1")) {
                                    Toast.makeText(ZhuanFaActivity.this,index.getMsg(),Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ZhuanFaActivity.this, index.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }.protocolBuffer(ZhuanFaActivity.this, Globals.ZL_URI, null);
                }


                break;
            case R.id.zhuanfa_quxiao:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode) {
            if (!(data == null)) {
                bundle2 = data.getBundleExtra("bundle2");
                strFromAct2 = bundle2.getString("strResult");
                ryid = bundle2.getString("strResult2");
                zhoujianrenText.setText(strFromAct2);
            }
        }
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
}
